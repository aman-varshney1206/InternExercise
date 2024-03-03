package org.ado.enums;

import lombok.Getter;

/***
 *
 */
@Getter
public enum FeatureManagement {
    BOARDS("ms.vss-work.agile"),
    REPOS("ms.vss-code.version-control"),
    PIPELINES("ms.vss-build.pipelines"),
    TEST_PLANS("ms.vss-test-web.test"),
    ARTIFACTS("ms.azure-artifacts.feature");

    private String featureId;

    FeatureManagement(String id) {
        featureId = id;
    }
}