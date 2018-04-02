package service;

import java.io.IOException;
import java.util.ArrayList;

import com.agent.domain.ApplicationLog;

public interface ApplicationLogService {
	public ArrayList<ApplicationLog> loadApplicationLogs(String filename) throws IOException;

}
