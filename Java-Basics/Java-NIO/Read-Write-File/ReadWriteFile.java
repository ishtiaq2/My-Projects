import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.nio.charset.Charset;
import java.util.*;
import static java.nio.file.StandardOpenOption.*;

public class ReadWriteFile {
	public static void main(String[] args) throws Exception {
		
		Set<OpenOption> options = new HashSet<OpenOption>();
		options.add(APPEND);
		options.add(CREATE);
		
		String encoding = System.getProperty("file.encoding");
		
		Path inputFile = Paths.get("./input.txt");
		Path outputFile = Paths.get("./output.txt");
		
		ByteChannel inputChannel = Files.newByteChannel(inputFile);
		ByteChannel outputChannel = Files.newByteChannel(outputFile, options);
		
		ByteBuffer buffer = ByteBuffer.allocate(124);
		int loopCount = 0;
				
		while (inputChannel.read(buffer) > 0) {
			buffer.flip();
			System.out.println(Charset.forName(encoding).decode(buffer));
			buffer.flip();
			outputChannel.write(buffer);
			buffer.clear();	
			System.out.print("Loop count: " + (++loopCount));
		}
	}
}
