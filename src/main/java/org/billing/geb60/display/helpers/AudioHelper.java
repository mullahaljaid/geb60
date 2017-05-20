package org.billing.geb60.display.helpers;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioHelper {

	private static final String WIN = "win.mp3";
	private static final String FAIL = "fail.mp3";
	private static final String POINTS = "points.mp3";

	private static void play(String fileName) {
		(new AudioPlayer(fileName)).start();
	}

	public static void playWin() {
		play(WIN);
	}
	
	public static void playFail() {
		play(FAIL);
	}
	
	public static void playPoints() {
		play(POINTS);
	}

	static class AudioPlayer extends Thread {

		private Log _log = LogFactory.getLog(getClass());
		
		private String soundFile = null;
		
		public AudioPlayer(String fileName) {
			this.soundFile = fileName;
		}
		
		@Override
		public void run() {
			try (final AudioInputStream in = AudioSystem
					.getAudioInputStream(AudioHelper.class.getClassLoader().getResourceAsStream(this.soundFile))) {
				final AudioFormat outFormat = getOutFormat(in.getFormat());
				final Info info = new Info(SourceDataLine.class, outFormat);

				try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {

					if (line != null) {
						line.open(outFormat);
						line.start();
						stream(AudioSystem.getAudioInputStream(outFormat, in), line);
						line.drain();
						line.stop();
					}
				}
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				_log.fatal("Error", e);
			}
		}
		
		private AudioFormat getOutFormat(AudioFormat inFormat) {
	        final int ch = inFormat.getChannels();
	        final float rate = inFormat.getSampleRate();
	        return new AudioFormat(Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
	    }

		private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
			final byte[] buffer = new byte[65536];
			for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
				line.write(buffer, 0, n);
			}
		}
	}
}
