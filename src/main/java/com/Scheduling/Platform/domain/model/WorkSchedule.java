package com.Scheduling.Platform.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "work_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek; // 1 (Segunda) a 7 (Domingo)

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "lunch_start")
    private LocalTime lunchStart;

    @Column(name = "lunch_end")
    private LocalTime lunchEnd;

    public boolean isWithinWorkHours(LocalTime time) {
        boolean withinWork = !time.isBefore(startTime) && time.isBefore(endTime);
        boolean isLunch = lunchStart != null && !time.isBefore(lunchStart) && time.isBefore(lunchEnd);
        return withinWork && !isLunch;
    }


}
