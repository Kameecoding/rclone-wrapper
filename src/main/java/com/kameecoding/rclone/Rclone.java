package com.kameecoding.rclone;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rclone implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(Rclone.class);
	private ProcessBuilder processBuilder;
	
	
	private Rclone() {}
	
	public static Rclone newInstance(String executable, List<String> args) {
		Rclone rclone =  new Rclone();
		List<String> arguments = new ArrayList<>(args);
		arguments.add(0, executable);
		rclone.processBuilder = new ProcessBuilder(arguments);
		return rclone;
	}
	
	@Override
	public void run() {
		LOGGER.trace("rclone running");
		try {
			processBuilder.redirectError(Redirect.INHERIT);
			processBuilder.redirectOutput(Redirect.INHERIT);
			LOGGER.info("Starting rclone with args {}",processBuilder.command());
			Process process = processBuilder.start();
			process.waitFor();
			LOGGER.info("rclone finished");
		} catch (Exception e) {
			LOGGER.error("rclone failed", e);
		}
		LOGGER.trace("rclone finished");
	}

}
