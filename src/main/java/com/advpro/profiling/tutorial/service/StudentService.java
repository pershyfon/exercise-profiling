package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> studentCourses = studentCourseRepository.findAll();
        Map<Long, Student> studentMap = new HashMap<>();
        for (Student student : students) {
            studentMap.put(student.getId(), student);
        }

        List<StudentCourse> studentCoursesResult = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            Student student = studentMap.get(studentCourse.getStudent().getId());
            StudentCourse newStudentCourse = new StudentCourse();
            newStudentCourse.setStudent(student);
            newStudentCourse.setCourse(studentCourse.getCourse());
            studentCoursesResult.add(newStudentCourse);
        }
        return studentCoursesResult;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findFirstByOrderByGpaDesc();
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            result.append(student.getName()).append(", ");
        }

        if (!result.isEmpty()) {
            result.delete(result.length() - 2, result.length());
        }
        return result.toString();
    }
}

