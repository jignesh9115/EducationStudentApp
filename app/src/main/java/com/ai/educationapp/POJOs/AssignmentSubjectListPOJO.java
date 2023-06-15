package com.ai.educationapp.POJOs;

import java.util.ArrayList;

public class AssignmentSubjectListPOJO {

    String id,subject_title;
    ArrayList<AssignmentPOJO> arrayList_assignment_list;

    public AssignmentSubjectListPOJO(String id, String subject_title, ArrayList<AssignmentPOJO> arrayList_assignment_list) {
        this.id = id;
        this.subject_title = subject_title;
        this.arrayList_assignment_list = arrayList_assignment_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    public ArrayList<AssignmentPOJO> getArrayList_assignment_list() {
        return arrayList_assignment_list;
    }

    public void setArrayList_assignment_list(ArrayList<AssignmentPOJO> arrayList_assignment_list) {
        this.arrayList_assignment_list = arrayList_assignment_list;
    }
}
