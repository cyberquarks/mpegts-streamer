package org.taktik.mpegts;


import java.io.File;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;

public class MTSSources {
	public static MTSSource fromSources(MTSSource... sources) {
		return fromSources(false, sources);
	}

	public static MTSSource fromSources(boolean fixContinuity, MTSSource... sources) {
		return MultiMTSSource.builder()
				.setFixContinuity(fixContinuity)
				.setSources(sources)
				.build();
	}

	public static MTSSource from(ByteChannel channel) throws IOException {
		return ByteChannelMTSSource.builder()
				.setByteChannel(channel)
				.build();
	}

	public static ResettableMTSSource from(SeekableByteChannel channel) throws IOException {
		return SeekableByteChannelMTSSource.builder()
				.setByteChannel(channel)
				.build();
	}

	public static ResettableMTSSource from(File file) throws IOException {
		return SeekableByteChannelMTSSource.builder()
				.setByteChannel(FileChannel.open(file.toPath()))
				.build();
	}

	public static MTSSource loop(ResettableMTSSource source) {
		return LoopingMTSSource.builder()
				.setSource(source)
				.build();
	}

	public static MTSSource loop(ResettableMTSSource source, int maxLoops) {
		return LoopingMTSSource.builder()
				.setSource(source)
				.setMaxLoops(maxLoops)
				.build();
	}
}
