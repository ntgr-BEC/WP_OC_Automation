Pre-Condition Checklist
1. Environment Setup

- Ensure Java 8 (version 1.8.0_xx) is installed and configured in your system PATH.
- Use any suitable IDE (e.g., IntelliJ, Eclipse) for running or debugging the automation.
- If running tests via Jenkins:
  - Ensure Jenkins is installed and configured.
  - Configure Jenkins jobs to point to the project directory.

2. Access Point (AP) Configuration
The following Access Points must be configured in the file:
C:\Web_Portal_Automation\com\src\test\resources\lconfig_dut.xml
AP ID	Type	Bundling
AP5	Real / Physical AP	Non-hard bundle
AP6	Real / Physical AP	Non-hard bundle
AP7	Dummy / Simulated	Non-hard bundle
AP8	Dummy / Simulated	Non-hard bundle
3. License Configuration
3 License Keys must be added in the file:
C:\Web_Portal_Automation\com\src\test\resources\licence.txt
4. Account Configuration
Account details should be defined in:
C:\Web_Portal_Automation\com\src\test\resources\config_webportal.xml
Account Type	Organization	Location
PRO Account	1 Organization	1 Location
Premium Account	Not required	1 Location

