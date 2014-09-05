package pl.java.scalatech.setting;



import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractSettings {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
    public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			return "test";
		}
	}
}
