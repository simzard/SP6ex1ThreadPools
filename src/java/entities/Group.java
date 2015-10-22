/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author simon
 */
public class Group {

    private String authors;
    private String classDescr;
    private String groupNo;

    public Group(String authors, String classDescr, String groupNo) {
        this.authors = authors;
        this.classDescr = classDescr;
        this.groupNo = groupNo;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getClassDescr() {
        return classDescr;
    }

    public void setClassDescr(String classDescr) {
        this.classDescr = classDescr;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }
    
}
