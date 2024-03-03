package org.ado.common.types;


import lombok.Getter;
import lombok.Setter;

/**
 * Response class for getting the resource area url.
 *
 */

@Getter @Setter
public class LocationUrl extends BaseAbstractMethod {
    private String id;
    private String name;
    private String locationUrl;
}
