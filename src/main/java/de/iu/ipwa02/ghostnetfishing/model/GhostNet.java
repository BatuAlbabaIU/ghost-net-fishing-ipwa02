package de.iu.ipwa02.ghostnetfishing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GhostNet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;
    private Double longitude;
    private String size;

    @Enumerated(EnumType.STRING)
    private GhostNetStatus status = GhostNetStatus.GEMELDET;

    private LocalDateTime reportedAt = LocalDateTime.now();

    // bergende Person (optional)
    private String salvorName;
    private String salvorPhone;

    // meldende Person (für "Verschollen" Pflicht)
    private String reporterName;
    private String reporterPhone;

    public Long getId() { return id; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public GhostNetStatus getStatus() { return status; }
    public void setStatus(GhostNetStatus status) { this.status = status; }

    public LocalDateTime getReportedAt() { return reportedAt; }

    public String getSalvorName() { return salvorName; }
    public void setSalvorName(String salvorName) { this.salvorName = salvorName; }

    public String getSalvorPhone() { return salvorPhone; }
    public void setSalvorPhone(String salvorPhone) { this.salvorPhone = salvorPhone; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getReporterPhone() { return reporterPhone; }
    public void setReporterPhone(String reporterPhone) { this.reporterPhone = reporterPhone; }
}
