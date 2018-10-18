package com.mea.domain;

import java.nio.file.Path;

public class BuildModelParameter {
	Path samleContextPath;
	Path sampleTypePath;
	
	Path testContextPath;
	Path testTypePath;
	
	Integer k;

	public Path getSamleContextPath() {
		return samleContextPath;
	}

	public void setSamleContextPath(Path samleContextPath) {
		this.samleContextPath = samleContextPath;
	}

	public Path getSampleTypePath() {
		return sampleTypePath;
	}

	public void setSampleTypePath(Path sampleTypePath) {
		this.sampleTypePath = sampleTypePath;
	}

	public Path getTestContextPath() {
		return testContextPath;
	}

	public void setTestContextPath(Path testContextPath) {
		this.testContextPath = testContextPath;
	}

	public Path getTestTypePath() {
		return testTypePath;
	}

	public void setTestTypePath(Path testTypePath) {
		this.testTypePath = testTypePath;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}
	
	
	
}
