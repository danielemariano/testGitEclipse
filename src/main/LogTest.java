package main;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

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
	private static final String gitHubWorkDir = "https://github.com/danielemariano/testGitEclipse";
	
	public static void main(String[] args) {
		Git git = cloneRepo();
		try {
			openRepository();
		} catch (IOException e) {
			e.printStackTrace();
		}
		printLog(git);
	}

	// Funzione per copiare in locale (in workDir) la repository remota (in gitHubWorkDir) 
	private static Git cloneRepo() {
		try {
			Git git=Git.cloneRepository()
			        .setURI(gitHubWorkDir)
			        .setDirectory(new File(workDir))
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
	
	// Funzione che apre la repository creata in locale per prendere alcune info
	private static Repository openRepository() throws IOException {
	    FileRepositoryBuilder builder = new FileRepositoryBuilder();

	    Repository repository = builder.setGitDir(new File(workDir))
	            .readEnvironment()
	            .findGitDir()
	            .build();
	     System.out.printf("Repository directory is {}", repository.getDirectory());

	    return repository;
	}
	
	// Funzione che stampa i messaggi di ogni commit
	private static void printLog(Git git) {
	    LogCommand log = git.log();
	    try {
	    	
	    	Iterator<RevCommit> com = log.call().iterator();

			while(com.hasNext()) {
		         Object element = com.next();
		         System.out.println(((RevCommit) element).getFullMessage());
		      }
			
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}
}