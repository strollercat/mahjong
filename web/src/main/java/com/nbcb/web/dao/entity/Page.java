package com.nbcb.web.dao.entity;

public class Page {
	// private int currentPage;
	// private int sizePerPage;

	private int start;
	private int end;
	private int rows;

	public int getRows() {
		return rows;
	}

	public Page(int currentPage, int rowsPerPage) {

		start = (currentPage - 1) * rowsPerPage;
		end = start + rowsPerPage - 1;
		this.rows = rowsPerPage;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String toString() {
		return "start[" + start + "]end[" + end + "]rows[" + rows + "]";
	}

}
