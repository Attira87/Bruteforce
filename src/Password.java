import java.io.IOException;


public interface Password {
	public String nextPassword() throws IOException, InterruptedException;

}
