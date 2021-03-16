package main;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


public class LogTest {
	
	private static final String workDir = "/Users/danielemariano/Desktop/test";

	public static void main(String[] args) {
		Git git = cloneRepo();
		
		/*try {
			openRepository();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		printLog(git);
	}

	private static Git cloneRepo() {
		try {
			Git git=Git.cloneRepository()
			        .setURI("https://github.com/danielemariano/testGitEclipse")
			        .setDirectory(new File("/Users/danielemariano/Desktop/test"))
			        .call();
			
			return git;
			
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Repository openRepository() throws IOException {
	    FileRepositoryBuilder builder = new FileRepositoryBuilder();

	    Repository repository = builder.setGitDir(new File("/Users/danielemariano/Desktop/test"))
	            .readEnvironment() // scan environment GIT_* variables
	            .findGitDir() // scan up the file system tree
	            .build();
	     System.out.printf("Repository directory is {}", repository.getDirectory());

	    return repository;
	}
	
	private static void printLog(Git git) {
	    LogCommand log = git.log();
	    try {
			RevCommit com = log.call().iterator().next();
			System.out.println(com.getFullMessage());
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}
}