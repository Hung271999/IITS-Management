package com.sharp.vn.its.management.dto.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharp.vn.its.management.entity.SystemEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type System dto.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemDTO {
    /**
     * The System id.
     */
    private Long systemId;
    /**
     * The System name.
     */
    private String systemName;

    /**
     * Instantiates a new System dto.
     *
     * @param system the system
     */
    public SystemDTO(SystemEntity system) {
        this.systemId = system.getId();
        this.systemName = system.getSystemName();
    }
}
