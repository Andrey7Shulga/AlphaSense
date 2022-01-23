# AlphaSense

Task description:
1. Create a small test automation framework for AlphaSense document page
https://rc.alpha-sense.com/doc/PR-386ea743f2a90399fb0e4300ddf37d0697abc743
(proposed combination of tools: Java/Kotlin, TestNG/Junit, Selenium, REST Assured)
a. Framework should be configurable
b. UI Test should follow Page Object design pattern
c. Test framework should provide test reporting with tests results at the end of tests
execution
d. Tests should use JSON format for web service interaction (not XML)
2. Test 1. Automate test scenario of:
a. Opening mentioned page
b. Searching for Additional Keyword “AlphaSense”
c. Scrolling to the last found result and clicking on it
d. Verifying that chosen statement is highlighted in the document viewer
3. Test 2. Sometimes we need to work on automation with lacking documentation. Figure
out what kind of request is being sent when searching for keywords and come up with
some (2-3) API tests for that particular endpoint. Let 1 of these tests be failing so we can
see details about the failure in a provided report.
