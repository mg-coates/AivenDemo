package io.aiven.demo.bst;

import com.fasterxml.jackson.annotation.*;
import java.util.UUID;

@JsonPropertyOrder(alphabetic = true)
public class TruckDTO {

    private UUID uuid;
    private float targetTemp;
    private float currentTemp;
    private java.time.Instant createdInstant = java.time.Instant.now();

    public TruckDTO() {
        this.uuid = UUID.randomUUID();
    }

    public TruckDTO(float targetTemp, float currentTemp) {
        this();
        this.targetTemp = targetTemp;
        this.currentTemp = currentTemp;
    }

    /**
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the targetTemp
     */
    public float getTargetTemp() {
        return targetTemp;
    }

    /**
     * @param targetTemp the targetTemp to set
     */
    public void setTargetTemp(float targetTemp) {
        this.targetTemp = targetTemp;
    }

    /**
     * @return the currentTemp
     */
    public float getCurrentTemp() {
        return currentTemp;
    }

    /**
     * @param currentTemp the currentTemp to set
     */
    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
        createdInstant = java.time.Instant.now();
    }

    /**
     * @return the createdInstant
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    public java.time.Instant getCreatedInstant() {
        return createdInstant;
    }

    /**
     * @param createdInstant the createdInstant to set
     */
    public void setCreatedInstant(java.time.Instant createdInstant) {
        this.createdInstant = createdInstant;
    }
}
