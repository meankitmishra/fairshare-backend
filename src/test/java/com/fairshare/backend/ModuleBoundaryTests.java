package com.fairshare.backend;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import java.util.List;
import org.junit.jupiter.api.Test;

class ModuleBoundaryTests {

  private static final String ROOT = "com.fairshare.backend";

  private static final List<String> MODULES =
      List.of(
          "auth",
          "profile",
          "groups",
          "expense",
          "ledger",
          "settlement",
          "payments",
          "receipts",
          "ocr",
          "analytics",
          "notifications",
          "activity",
          "sync");

  private static final JavaClasses PRODUCTION_CLASSES =
      new ClassFileImporter()
          .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
          .importPackages(ROOT);

  /** A module's {@code internal} package must never be referenced from outside that module. */
  @Test
  void moduleInternalsAreNotAccessedFromOtherModules() {
    for (String module : MODULES) {
      String modulePackage = ROOT + "." + module + "..";
      String internalPackage = ROOT + "." + module + ".internal..";

      ArchRule rule =
          noClasses()
              .that()
              .resideOutsideOfPackage(modulePackage)
              .should()
              .dependOnClassesThat()
              .resideInAPackage(internalPackage)
              .as("internals of module '" + module + "' must only be used within that module")
              .allowEmptyShould(true);

      rule.check(PRODUCTION_CLASSES);
    }
  }

  /** There must be no dependency cycles between the top-level module packages. */
  @Test
  void modulesAreFreeOfCycles() {
    slices().matching(ROOT + ".(*)..").should().beFreeOfCycles().check(PRODUCTION_CLASSES);
  }
}
