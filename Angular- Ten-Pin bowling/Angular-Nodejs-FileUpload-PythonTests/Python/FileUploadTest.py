from selenium import webdriver
import time

driver = webdriver.Chrome()
driver.get("http://localhost:8000")
assert "Local App Frontend Upload File" in driver.title

select_file = driver.find_element_by_id("select_file")
select_file.click();
time.sleep(3)
select_file.clear();
time.sleep(2)
select_file.send_keys("/home/ishtiaq/textfile")
time.sleep(5)

send_file = driver.find_element_by_id("upload_file")
send_file.click()

time.sleep(5)

driver.close()