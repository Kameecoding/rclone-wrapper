/*
 *
 *  MIT License
 *
 * <p>Copyright (c) 2018 Andrej Kovac (Kameecoding)
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 *  substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.kameecoding.rclone;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrej Kovac kameecoding (kamee@kameecoding.com) on 2018-02-03
 *
 */
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
