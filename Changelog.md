# Change Log

# 1.3.0
- Spaces in data is written to csv file are not converted to commas.
- Added "check_run.bat", to help debug when users have issues.

# 1.2.0
- Data is written to new csv file. Determined by current time down to the minute. If launching twice within the minute it will still use the same data file as the first time.
- Karta will try and write error messages to "karta.log" first if possible instead of printing it in the console.
- LICENSE.md is copied to target at build.

# 1.1.0
- Added clipboard test filter, only text containing "/jumploc" will be appended to file.
- Changed project structure, separating source and compiled code more clearly.

# 1.0.0
- Basic Java program to record locations from clipboard. 