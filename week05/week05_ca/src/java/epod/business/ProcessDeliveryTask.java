package epod.business;

import epod.web.EPODSessions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.StreamMessage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.sql.DataSource;

public class ProcessDeliveryTask implements Runnable {

	private static final String INSERT_SQL = "insert into ack(ack_id, team_id, pod_id, img_path) values (?, ?, ?, ?)";

	private static final Logger LOGGER = Logger.getLogger(ProcessDeliveryTask.class.getName());

	public static final int BUFF_8K = 1024 * 8;

	private final StreamMessage msg;
	private final String imgDir;
	private final EPODSessions sessions;
	private final DataSource dataSource;

	public ProcessDeliveryTask(StreamMessage m, String d, EPODSessions sess, DataSource ds) {
		msg = m;
		imgDir = d;
		sessions = sess;
		dataSource = ds;
	}

	@Override
	public void run() {
		String teamId, note, callback, imgType;
		long podId;
		int imgSize;
		byte[] buffer = new byte[BUFF_8K];

		try {
			msg.reset();
			teamId = msg.readString();
			podId = msg.readLong();
			note = msg.readString();
			callback = msg.readString();
			imgType = msg.readString();
			imgSize = msg.readInt();
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFF_8K)) {
				int count = 0;
				while (count < imgSize) { 
					int sz = msg.readBytes(buffer);
					baos.write(buffer, 0, sz);
					count += sz;
				}
				buffer = baos.toByteArray();
			}
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, "Error message", ex);
			return;
		}

		Path imgPath;
		try {
			imgPath = create(teamId, podId, imgType);
			Files.write(imgPath, buffer, StandardOpenOption.CREATE);
		} catch (IOException ex) {
			LOGGER.log(Level.WARNING, "Writing image", ex);
			return;
		}

		Optional<String> opt = WebClient.call(callback, podId);
		if (!opt.isPresent())
			return;

		String ackId = opt.get();

		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
			ps.setString(1, ackId);
			ps.setString(2, teamId);
			ps.setLong(3, podId);
			ps.setString(4, imgPath.toFile().getAbsolutePath());
			if (1 != ps.executeUpdate())
				LOGGER.log(Level.WARNING, "Insert error");
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Logging ack", ex);
		}

		final JsonObject data = Json.createObjectBuilder()
				.add("teamId", teamId)
				.add("podId", podId)
				.add("ackId", ackId)
				.add("note", note)
				.add("pod", teamId + "/" + imgPath.toFile().getName())
				.build();

		sessions.readMutex(() -> {
			sessions.broadcast(data);
		});
	}

	private FileAttribute<Set<PosixFilePermission>> perms(String str) {
		Set<PosixFilePermission> p = PosixFilePermissions.fromString(str);
		return (PosixFilePermissions.asFileAttribute(p));
	}

	private Path create(String teamId, long podId, String suffix) throws IOException {
		Path dir = Paths.get(imgDir, teamId);

		if (!Files.isDirectory(dir)) {
			//Files.createDirectory(dir, perms("rwxr-xr-x"));
			LOGGER.log(Level.INFO, "Create image dir: %s", 
					dir.toFile().getAbsolutePath());
			dir.toFile().mkdir();
		}


		String rnd = UUID.randomUUID().toString().substring(0, 4);

		Path imgFile = Paths.get(dir.toString(), rnd + "_" + Math.abs(podId) + "." + suffix);

		File f = imgFile.toFile();
		return (f.toPath());

		//return (Files.createFile(imgFile, perms("rwxr-xr-x")));
	}
}
