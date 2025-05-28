import re
import time

import requests
from selenium import webdriver
from selenium.webdriver.common.by import By
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from selenium.webdriver.chrome.options import Options
import os

print("Starting report.py")


def get_build_data_from_jenkins_return_url():
    # Fetch Jenkins environment variables
    jenkins_url = os.getenv('JENKINS_URL')
    job_name = os.getenv('JOB_NAME')
    build_number = os.getenv('BUILD_NUMBER')

    # Print Jenkins environment variables for debugging
    print(f"JENKINS_URL = {jenkins_url}")
    print(f"JOB_NAME = {job_name}")
    print(f"BUILD_NUMBER = {build_number}")

    # Construct the Jenkins URL
    url = f"http://172.16.27.12:8081/job/{job_name}/{build_number}/consoleText"
    print("jenkins live url = ", url)
    return url


def get_data_from_jenkins_api(jenkins_url):
    url = jenkins_url
    username = "admin"
    password = "Netgear1@1"
    response = requests.get(url, auth=(username, password))

    if response.status_code == 200:
        log_data = response.text
        # print("log_data = ", log_data)  # Full console log
        print("data got it from jenkins")
        return log_data
    else:
        print(f"Failed to fetch log. Status code: {response.status_code}")


# Function to dynamically extract test results from a log string
def extract_test_results_from_string(log_data):
    test_results = []
    execution_details = {}

    # Regex patterns for test cases and summary lines
    # test_case_pattern = re.compile(r"Stop TestCase\((\w+)\):\s*(webportal\.ApiTest\.(\w+)\.(\w+))\.test")
    test_case_pattern = re.compile(
        r"Stop TestCase\((\w+)\):\s*webportal\.ApiTest(?:\.(\w+))+(?:\.(\w+))+(?:\.(\w+))?\.test")
    test_module_pattern = r""

    test_result_pattern = re.compile(r"Testcase Count:\s*(\d+)\(P\)/(\d+)\(F\)/(\d+)\(Total\)")

    # Convert the log into lines
    lines = log_data.splitlines()

    # Temporary storage for current block of test cases
    current_block = []
    matchline = []

    for line in lines:
        # Match test case lines
        test_case_match = test_case_pattern.search(line)
        if test_case_match:
            matchline.append(test_case_match)
            result, full_name, module_name, test_name = test_case_match.groups()
            current_block.append({
                "name": module_name,
                "module": full_name,
                "result": result
            })
            # Add the current block to test results
            test_results.extend(current_block)
            current_block = []

            # Match summary line and finalize the current block
        test_result_match = test_result_pattern.search(line)
        if test_result_match:
            pass_count, fail_count, total_count = map(int, test_result_match.groups())
            execution_details = {
                "pass": pass_count,
                "fail": fail_count,
                "total": total_count
            }

            # Reset for the next block

    # Validate the count matches
    if len(test_results) != execution_details.get("total", 0):
        print("Warning: Extracted test cases count does not match total reported count.")

    print("Test Results Count:", test_results)
    print("Execution Details:", execution_details)
    print("matchline = > ", len(matchline))
    return test_results, execution_details


# Function to generate an HTML report dynamically based on test results
def generate_html_report(test_results, execution_details):
    # Define the output file
    output_file = "api_test_report.html"

    # Create the basic structure of the report
    html_content = """
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Insight Nightly Build Test Report - Pri-qa Server</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            table { border-collapse: collapse; width: 100%; margin-top: 20px; }
            table, th, td { border: 1px solid black; padding: 8px; text-align: left; }
            th { background-color: #f2f2f2; }
            .summary { margin-top: 20px; font-weight: bold; }
            .pass { color: green; }
            .fail { color: red; }
            .accepted { color: green; }
            .stable { color: orange; }
            .unstable { color: red; }
        </style>
    </head>
    <body>
        <h1>API Test Report</h1>
    """

    # Count the number of passes and fails
    pass_count = execution_details.get("pass", 0)
    fail_count = execution_details.get("fail", 0)
    total_tests = execution_details.get("total", 0)

    # Calculate pass percentage
    pass_percentage = (pass_count / total_tests) * 100 if total_tests > 0 else 0
    fail_percentage = (fail_count / total_tests) * 100 if total_tests > 0 else 0

    # Determine build status based on pass percentage
    if pass_percentage >= 90:
        status = "BUILD ACCEPTED"
        status_class = "accepted"
    elif 80 <= pass_percentage < 90:
        status = "BUILD STABLE"
        status_class = "stable"
    else:
        status = "BUILD UNSTABLE"
        status_class = "unstable"

    # Add Execution Status section
    html_content += f"""
    <h2 style="color: black; font-weight: bold;">Execution Status:</h2>
    <h2 class="{status_class}"><b>{status}</b></h2>
    <p><b>Pass Percentage:</b> <span style="color: green;">{pass_percentage:.2f}%</span></p>
    <p><b>Fail Percentage:</b> <span style="color: red;">{fail_percentage:.2f}%</span></p>
    <p><b>Total Tests:</b> {total_tests}</p>
   <div class="summary">
            <p class="pass">Passed: {pass_count}</p>
            <p class="fail">Failed: {fail_count}</p>
        </div>
    """

    # Add a table with test case data (removing 'module' column)
    html_content += """
        <table>
            <tr>
                <th>Test Case Name</th>
                <th>Result</th>
            </tr>
    """

    # Add test cases to the table (use 'name' as test case name)
    for test in test_results:
        result_class = "pass" if test["result"].lower() == "pass" else "fail"
        html_content += f"""
            <tr>
                <td>{test['name']}</td>
                <td class="{result_class}">{test['result']}</td>
            </tr>
        """

    # Add summary and closing remarks
    html_content += """
        </table>
        <div class="Regards">
            <p>Regards,</p>
            <p>BEC Automation Team</p>
        </div>
    </body>
    </html>
    """

    # Check if the file exists and delete it if it does
    if os.path.exists(output_file):
        os.remove(output_file)
        print("Old HTML file is deleted.")

    time.sleep(2)

    # Save the HTML report
    with open(output_file, "w") as report_file:
        report_file.write(html_content)
        print("New HTML report file is created.")

    return html_content


def send_email(subject, body, attachment_path=None):
    from_email = "insightalertsbec@gmail.com"
    from_password = "tbhy ombl ukae edyr"
    smtp_server = "smtp.gmail.com"
    smtp_port = 587
    # to_email = ["sumanta.jena@netgear.com", "bec-mani-team@netgear.com", "awaghe@netgear.com",
    #             "rahul.gautam@netgear.com", "vaibhav.srivastav@netgear.com", "sumit.kumar@netgear.com",
    #             "pankaj.joshi@netgear.com", "sudhir.vats@netgear.com"]
    # cc_emails = ["sumanta.jena@netgear.com", "vivekkumar.singh@netgear.com",
    #              "tejeshwini.vishwanath@netgear.com"]  # Add your CC email addresses here

    to_email = ["vivekkumar.singh@netgear.com"]
    cc_emails = ["sumanta.jena@netgear.com","rsubramani@netgear.com"]
    server = None

    # Create the MIME message
    msg = MIMEMultipart()
    msg['From'] = from_email
    msg['To'] = ", ".join(to_email)  # Convert list to comma-separated string
    msg['Cc'] = ", ".join(cc_emails)
    msg['Subject'] = subject

    # Attach the email body as HTML
    msg.attach(MIMEText(body, 'html'))

    if attachment_path:
        # Open the file to be attached
        with open(attachment_path, 'rb') as attachment:
            # Create a MIMEImage object
            img = MIMEImage(attachment.read(), _subtype="png")
            # Add a header to indicate the filename
            img.add_header('Content-Disposition', 'attachment', filename=os.path.basename(attachment_path))
            # Attach the image to the email
            msg.attach(img)

    try:
        # Connect to the SMTP server
        server = smtplib.SMTP(smtp_server, smtp_port)
        server.starttls()  # Secure the connection
        server.login(from_email, from_password)  # Log in to the email account
        print("Server is connected")

        # Send the email
        all_recipients = to_email + cc_emails  # Combine To and CC recipients
        server.sendmail(from_email, all_recipients, msg.as_string())
        print("Email sent successfully")

    except Exception as e:
        print(f"Failed to send email: {e}")

    finally:
        if server:
            server.quit()


def main():
    version_data = None
    job_url = get_build_data_from_jenkins_return_url()#
    #job_url = "http://172.16.27.12:8081/job/switch_webportal_ap_exec03/789/consoleText"
    data = get_data_from_jenkins_api(job_url)
    result = extract_test_results_from_string(log_data=data)

    subject = "Nightly Build API Test Report"
    html_data = generate_html_report(test_results=result[0], execution_details=result[1])
    attachment_file_path = "test_case_report.html"
    send_email(subject=subject, body=html_data, attachment_path=None)


main()
