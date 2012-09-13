package com.phaxio.util;

import java.util.List;

public class PagedList<T> {

    List<T> elements;
    int maxPerPage;
    int page;
    int totalPages;
    int totalResults;

    public List<T> getElements() {
        return elements;
    }

    public int getMaxPerPage() {
        return maxPerPage;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public void setMaxPerPage(int maxPerPage) {
        this.maxPerPage = maxPerPage;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }


}
