  public void initOptions(CompilerOptions options) {
    this.options = options;
    if (errorManager == null) {
      if (outStream == null) {
        setErrorManager(
            new LoggerErrorManager(createMessageFormatter(), logger));
      } else {
        PrintStreamErrorManager printer =
            new PrintStreamErrorManager(createMessageFormatter(), outStream);
        printer.setSummaryDetailLevel(options.summaryDetailLevel);
        setErrorManager(printer);
      }
    }
    if (options.enables(DiagnosticGroups.CHECK_TYPES)) {
      options.checkTypes = true;
    } else if (options.disables(DiagnosticGroups.CHECK_TYPES)) {
      options.checkTypes = false;
    } else if (!options.checkTypes) {
      options.setWarningLevel(
          DiagnosticGroup.forType(
              RhinoErrorReporter.TYPE_PARSE_ERROR),
          CheckLevel.OFF);
    }
    if (options.checkGlobalThisLevel.isOn()) {
      options.setWarningLevel(
          DiagnosticGroups.GLOBAL_THIS,
          options.checkGlobalThisLevel);
    }
    List<WarningsGuard> guards = Lists.newArrayList();
    guards.add(
        new SuppressDocWarningsGuard(
            getDiagnosticGroups().getRegisteredGroups()));
    guards.add(options.getWarningsGuard());
   if (!options.checkSymbols) {
      guards.add(new DiagnosticGroupWarningsGuard(
          DiagnosticGroups.CHECK_VARIABLES, CheckLevel.OFF));
    }
    this.warningsGuard = new ComposeWarningsGuard(guards);
  }
