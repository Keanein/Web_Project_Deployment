// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


// Main entry point of the COS Attendance Spring Boot application
@SpringBootApplication
@EntityScan("edu.uep.cos.attendance.entity")
@EnableJpaRepositories("edu.uep.cos.attendance.repository")
public class CosAttendanceBackendApplication {

    // Starts and runs the Spring Boot application
    public static void main(String[] args) {
        SpringApplication.run(CosAttendanceBackendApplication.class, args);
    }

}