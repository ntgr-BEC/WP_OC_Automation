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
    url = f"http://172.16.27.12:8081/job/{job_name}/{build_number}/consoleFull"
    print("jenkins live url = ", url)
    return url


def get_test_log_from_jenkins():
    # global driver
    chrome_options = Options()
    # chrome_options.add_argument("--headless")  # Run Chrome in headless mode
    chrome_options.add_argument("--no-sandbox")
    chrome_options.add_argument("--disable-dev-shm-usage")

    # Ensure the ChromeDriver path is correctly set up
    driver = webdriver.Chrome(options=chrome_options)  # download the latest chrome driver automatically
    driver.get("http://172.16.27.12:8081/login?from=/")
    driver.maximize_window()
    time.sleep(3)
    driver.find_element(By.ID, "j_username").send_keys("admin")
    driver.find_element(By.NAME, "j_password").send_keys("Netgear1@1")
    driver.find_element(By.NAME, "Submit").click()
    time.sleep(5)
    driver.get("http://172.16.27.12:8081/job/Insight_Automation_Nightly_Build_01/")
    time.sleep(5)
    driver.find_element(By.XPATH,
                        '(//div[@id="buildHistory"]//td[@class="build-row-cell"]//a[@update-parent-class=".build-row"])[1]').click()
    time.sleep(2)
    try:
        driver.find_element(By.XPATH,
                            '(//div[@id="buildHistory"]//td[@class="build-row-cell"]//a[@update-parent-class=".build-row"])[1]').click()
    except:
        pass
    time.sleep(5)
    base_url = driver.current_url
    print("base_url = ", base_url)
    driver.find_element(By.XPATH, '//a[@title="Console Output"]').click()
    final_url = f"{base_url}consoleFull"
    print("final_url = ", final_url)
    # driver.get(url=final_url)
    time.sleep(5)
    driver.find_element(By.XPATH, '//a[@href="consoleFull"]').click()
    # driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
    time.sleep(30)
    # Print the page title to verify if the correct page is loaded
    print("Page title after navigation:", driver.title)
    log_data = driver.find_element(By.XPATH, '//pre[@class="console-output"]').text
    driver.quit()
    return log_data


def get_data_from_jenkins_api():
    chrome_options = Options()
    chrome_options.add_argument("--headless")  # Run Chrome in headless mode
    chrome_options.add_argument("--no-sandbox")
    chrome_options.add_argument("--disable-dev-shm-usage")

    # Ensure the ChromeDriver path is correctly set up
    driver = webdriver.Chrome(options=chrome_options)  # download the latest chrome driver automatically
    driver.get("http://172.16.27.12:8081/login?from=/")
    driver.maximize_window()
    time.sleep(3)
    driver.find_element(By.ID, "j_username").send_keys("admin")
    driver.find_element(By.NAME, "j_password").send_keys("Netgear1@1")
    driver.find_element(By.NAME, "Submit").click()
    time.sleep(5)
    driver.get("http://172.16.27.12:8081/job/Insight_Automation_Nightly_Build_01/")
    time.sleep(5)
    driver.find_element(By.XPATH,
                        '(//div[@id="buildHistory"]//td[@class="build-row-cell"]//a[@update-parent-class=".build-row"])[1]').click()
    time.sleep(2)
    try:
        driver.find_element(By.XPATH,
                            '(//div[@id="buildHistory"]//td[@class="build-row-cell"]//a[@update-parent-class=".build-row"])[1]').click()
    except:
        pass
    time.sleep(5)
    base_url = driver.current_url
    print("base_url = ", base_url)

    url = f"{base_url}consoleText"
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


def login_and_verify_its_login_or_not(driver):
    url = "https://pri-qa.insight.netgear.com/#/landingPage"
    login_now_button = '//button[@id="loginNow" and text()="Login Now"]'
    username = "//input[@formcontrolname='email']"
    pwd = "//input[@formcontrolname='password']"
    sign_in_button = "//span[text()='NETGEAR Sign In']"
    time.sleep(3)
    driver.get(url=url)
    driver.maximize_window()
    time.sleep(20)
    for i in range(20):
        driver.find_element(By.XPATH, login_now_button).click()
        time.sleep(20)
        try:
            print(f"trying to login {i+1} times ")
            driver.find_element(By.XPATH, username).send_keys("a6proviku@yopmail.com")
            time.sleep(2)
            driver.find_element(By.XPATH, pwd).send_keys("Netgear1@")
            time.sleep(2)
            driver.find_element(By.XPATH, sign_in_button).click()
            time.sleep(40)
        except:
            driver.get(url)
            time.sleep(10)
            pass
        try:
            time.sleep(2)
            driver.find_element(By.XPATH, login_now_button)
        except:
            break


def get_version():
    about = "//li/a[text()='About']"
    web_portal_version = '//div[@class="InsightVirsion"]/p[1]'
    Cloud_Version = '//div[@class="InsightVirsion"]/p[2]'
    humburger_menu = '//ul[@class="header-dropdown-list padding-cloak5"]'
    # humburger_menu = '// *[ @ id = "header"]/div[2]/div/ul/li/div/div/div[2]'

    chrome_options = Options()
    # chrome_options.add_argument("--headless")  # Run Chrome in headless mode
    chrome_options.add_argument("--no-sandbox")
    chrome_options.add_argument("--disable-dev-shm-usage")
    driver = webdriver.Chrome(options=chrome_options)  # download the latest chrome driver automatically
    login_and_verify_its_login_or_not(driver)
    time.sleep(15)
    driver.find_element(By.XPATH, humburger_menu).click()
    time.sleep(5)
    driver.find_element(By.XPATH, about).click()
    time.sleep(5)
    web_portal_version_text = driver.find_element(By.XPATH, web_portal_version).text
    Cloud_Version_text = driver.find_element(By.XPATH, Cloud_Version).text
    print("yes, found the Versions data", web_portal_version_text, Cloud_Version_text)
    return web_portal_version_text, Cloud_Version_text


# Function to dynamically extract test results from a log string
def extract_test_results_from_string(log_data):
    test_results = []
    test_results.clear()
    execution_details = {}

    test_id_pattern = re.compile(r"Run TestCase:\s*(\S+)")
    test_name_pattern = re.compile(r"TestCase Summary:\s*(.+)")
    test_result_pattern = re.compile(r"Stop TestCase\((Pass|Fail)\):\s*(\S+)")
    time_pattern = re.compile(r"Total time:\s*(\d+:\d+)\s*(h|min|s)")

    lines = log_data.splitlines()
    current_test = {}

    for line in lines:
        test_id_match = test_id_pattern.search(line)
        if test_id_match:
            if current_test:
                test_results.append(current_test)
            current_test = {"id": test_id_match.group(1), "name": "", "result": "Unknown"}

        test_name_match = test_name_pattern.search(line)
        if test_name_match and current_test:
            current_test["name"] = test_name_match.group(1)

        test_result_match = test_result_pattern.search(line)
        if test_result_match:
            result = test_result_match.group(1)  # "Pass" or "Fail"
            test_id = test_result_match.group(2)
            if current_test and current_test.get("id") == test_id:
                current_test["result"] = result

        time_match = time_pattern.search(line)
        if time_match:
            execution_details["total_time"] = f"{time_match.group(1)} {time_match.group(2)}"

    if current_test:
        test_results.append(current_test)

    print("test_results = ", test_results)
    print("execution_details = ", execution_details)
    return test_results, execution_details


# Function to generate an HTML report dynamically based on test results
def generate_html_report(test_results, execution_details, version_data, output_file):
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
        <h1>Daily Insight Nightly Build Test Report</h1>
        
        
        
    """

    # Count the number of passes and fails
    pass_count = sum(1 for test in test_results if test["result"].lower() == "pass")
    fail_count = sum(1 for test in test_results if test["result"].lower() == "fail")
    total_tests = len(test_results)

    # Calculate pass percentage
    pass_percentage = (pass_count / total_tests) * 100 if total_tests > 0 else 0
    fail_percentage = (fail_count / total_tests) * 100 if total_tests > 0 else 0

    # Determine build status based on pass percentage
    if pass_percentage >= 85:
        status = "BUILD ACCEPTED"
        status_class = "accepted"
    elif 70 <= pass_percentage < 85:
        status = "BUILD STABLE"
        status_class = "stable"
    else:
        status = "BUILD UNSTABLE"
        status_class = "unstable"

    # Add Execution Status section with updated formatting
    html_content += f"""
    <h2 style="color: black; font-weight: bold;">Server Details:</h2>
    <h3>Pri-qa Server</h3>
    <p><span style="color: black;"><b>{version_data[0]}</b></span></p>
    <p><span style="color: black;"><b>{version_data[1]}</b></span></p>

    <h2 black; font-weight: bold;">Execution Status:</h2>
    <h2 class="{status_class}"><b>{status}</b></h2>
    <p><b>Pass Percentage:</b> <span style="color: green;">{pass_percentage:.2f}%</span></p>
    <p><b>Fail Percentage:</b> <span style="color: red;">{fail_percentage:.2f}%</span></p>
    <p><b>Total Time:</b> {execution_details.get('total_time', 'N/A')}</p>
    """

    # Add a table with test case data
    html_content += """
        <table>
            <tr>
                <th>Test Case ID</th>
                <th>Test Case Name</th>
                <th>Result</th>
            </tr>
    """

    # Add test cases to the table
    for test in test_results:
        result_class = "pass" if test["result"].lower() == "pass" else "fail"
        id = test['id'].split(".")
        html_content += f"""
            <tr>
                <td>{id[2]}</td>
                <td>{test['name']}</td>
                <td class="{result_class}">{test['result']}</td>
            </tr>
        """

    # Add summary of passes and fails
    html_content += f"""
        </table>
        <div class="summary">
            <p>Total Tests: {total_tests}</p>
            <p class="pass">Passed: {pass_count}</p>
            <p class="fail">Failed: {fail_count}</p>
        </div>
        
        <div class="Regards">
            <p>Regards,</p>
            <p>BEC Automation Team</p>
        </div>
    </body>
    </html>
    """

    # # Check if the file exists and delete it if it does
    # if os.path.exists(output_file):
    #     os.remove(output_file)
    #     print("Old Html File is Deleted")
    # time.sleep(2)
    # # Save the HTML report
    # with open(output_file, "w") as report_file:
    #     report_file.write(html_content)
    #     print("New Html Report File is created")

    output_dir = os.path.dirname(output_file)
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
        print(f"Directory created: {output_dir}")

    # Check if the file exists and delete it if it does
    if os.path.exists(output_file):
        os.remove(output_file)
        print("Old HTML file is deleted")

    time.sleep(2)

    # Save the HTML report
    with open(output_file, "w") as report_file:
        report_file.write(html_content)
        print("New HTML report file is created")

    return html_content


def send_email(subject, body, attachment_path):
    from_email = "insightalertsbec@gmail.com"
    from_password = "tbhy ombl ukae edyr"
    smtp_server = "smtp.gmail.com"
    smtp_port = 587
    # to_email = ["sumanta.jena@netgear.com", "bec-mani-team@netgear.com", "awaghe@netgear.com",
    #             "rahul.gautam@netgear.com", "vaibhav.srivastav@netgear.com", "sumit.kumar@netgear.com",
    #             "pankaj.joshi@netgear.com", "sudhir.vats@netgear.com", "divya.trivedi@netgear.com"]
    # cc_emails = ["sumanta.jena@netgear.com", "vivekkumar.singh@netgear.com",
    #              "tejeshwini.vishwanath@netgear.com"]  # Add your CC email addresses here

    to_email = ["sumanta.jena@netgear.com"]
    cc_emails = ["sumanta.jena@netgear.com", "vivekkumar.singh@netgear.com",
                 "tejeshwini.vishwanath@netgear.com"]  # Add your CC email addresses here
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
    # job_url = get_build_data_from_jenkins_return_url()
    # job_url= "http://172.16.27.12:8081/job/Insight_Automation_Nightly_Build_01/668/consoleFull"
    # print("jenkins offline url = ", job_url)
    data = get_data_from_jenkins_api()
    # data = get_test_log_from_jenkins()
    # print(data)
    try:
        version_data = get_version()
    except:
        pass
    print("version data", version_data)
    if version_data is None:
        version_data = ('', '')
    result = extract_test_results_from_string(log_data=data)
    subject = "Daily Insight Nightly Build Test Report - Pri-qa Server"
    attachment_file_path = r"C:\Web_Portal_Automation\com\bin\python\nightlyBuild_Testreport.html"
    html_data = generate_html_report(test_results=result[0], execution_details=result[1], version_data=version_data,
                                     output_file=attachment_file_path)
    send_email(subject=subject, body=html_data, attachment_path=attachment_file_path)


main()
