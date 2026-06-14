package com.fairshare.backend;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {
  static final ApplicationModules MODULES = ApplicationModules.of(FairshareApplication.class);

  @Test
  void verifiesModularStructure() {
    MODULES.verify();
  }

  @Test
  void rendersDocs() {
    new Documenter(MODULES)
        .writeModulesAsPlantUml()
        .writeIndividualModulesAsPlantUml()
        .writeModuleCanvases();
  }
}
