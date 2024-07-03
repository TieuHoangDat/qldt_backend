package com.ptit.qldt.models;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`Groups`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "day_of_week", nullable = false)
    private int dayOfWeek;

    @Column(name = "period", nullable = false)
    private int period;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Account teacher;

    @Column(nullable = false)
    private String room;

    @Column(name = "max_students", nullable = false)
    private int maxStudents;

    @Column(name = "available_slots", nullable = false)
    private int availableSlots;

    @ManyToOne
    @JoinColumn(name = "term", nullable = false)
    private Term term;
}
