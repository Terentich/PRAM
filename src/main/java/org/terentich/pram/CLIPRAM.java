package org.terentich.pram;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CLIPRAM {

	private static final String OPTION_FILE_NAME = "f";
	private static final String OPTION_FILE_LONG_NAME = "file";
	private static final String OPTION_FILE_ARG_NAME = "FILE";
	private static final String OPTION_FILE_DESCRIPTION = "specify PRAM source file";

	private static final String OPTION_HELP_NAME = "h";
	private static final String OPTION_HELP_LONG_NAME = "help";
	private static final String OPTION_HELP_DESCRIPTION = "print this message";

	private final String APPLICATION_CLI_NAME;

	private List<File> inputFiles = new ArrayList<File>();

	CLIPRAM(String cliName) {
		APPLICATION_CLI_NAME = cliName;
	}

	public void parseArgs(String[] args) {
		try {
			CommandLineParser parser = new PosixParser();
			Options options = getOptions();
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption(OPTION_FILE_NAME)) {
				handleOptionFile(cmd.getOptionValues(OPTION_FILE_NAME));
			} else if (cmd.hasOption(OPTION_HELP_NAME)) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(APPLICATION_CLI_NAME, options);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void handleOptionFile(String[] values) {
		for (String value : values) {
			inputFiles.add(new File(value));
		}
	}

	List<File> getInputFiles() {
		return inputFiles;
	}

	private Options getOptions() {

		Option optionFile = new Option(OPTION_FILE_NAME,
				OPTION_FILE_DESCRIPTION);
		optionFile.setLongOpt(OPTION_FILE_LONG_NAME);
		optionFile.setArgName(OPTION_FILE_ARG_NAME);
		optionFile.setArgs(Option.UNLIMITED_VALUES);

		Option optionHelp = new Option(OPTION_HELP_NAME,
				OPTION_HELP_DESCRIPTION);
		optionHelp.setLongOpt(OPTION_HELP_LONG_NAME);

		Options options = new Options();
		options.addOption(optionFile);
		options.addOption(optionHelp);

		return options;
	}
}
