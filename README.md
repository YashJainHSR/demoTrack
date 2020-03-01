# demoTrack
A Cloud based Web Application

## Problem Statement: 
For a Product company selling various kinds of products in bulk, customer would want an illustration of the product before they purchase it, to get all the details about the product ironed out before making the purchase.
That can be done by a sales representative giving the product illustration to the customer and finalize the deal but this way
-There are no records maintained for the illustrations
-No authorization procedure
-No digital communication to the concerned parties
-No way to analyze the past experiences for improvement
So, is there a better way?

There is!!

## demoTrack
demoTrack is a cloud-based solution (Web Application), which offers a vast variety of features to make this process better and hassle free.

### demoTrack offers:
-digital record maintenance for demos
-demo Authorization
-digital communication
-feedback feature to improve future demo’s
-the company can keep track of all the demos, sales executive performance and how to improve the executive’s performance.

This will also help the company to analyze the customer’s response.

## An Overview of the application flow

	-Using demoTrack, a sales executive can generate a request for an illustration/demo providing all the relevant details pertaining to the demo and he will be provided with a request number for future reference
	-Approver/manager will be digitally notified of the request generated and he/she can verify the details and decide whether to approve the demo or not.
	-Sales executive will be digitally notified of the approver’s decision
	-If the demo is approved, the sales executive can proceed with the demo
	-After the demo is given, sales executive is bound to fill a feedback for that demo before he/she can request for another demo. This feedback will help him improve.
	-Executives, Managers and Admin can generate reports in excel format (records can be filtered based on requirement)
	-Administrator can keep track of the demos, add new users and new products using UI.

## Technology Stack Used:
	-Angular (v8.3.23)
	-Spring Boot
	-MySQL
	-Azure Cloud

## Coding Standards: 

    -Modularity
    -Descriptive name conventions (full words – camel casing) (e.g. demoRequest)
    -Component/Class names represents its purpose (e.g. DemoRequestController, LoginComponent, AuthenticationService)
	-Encapsulation
    -Proper Indentation
    -Proper response status codes (e.g. 201, 401…)
	-Server-Side Validations (e.g. Email format, password requirements...)
    -Error/Exception Handling (e.g. Null Pointer exception on User Not found)
    -Custom Error Message (e.g. User Already Exists, Invalid Credentials…)
	-Client-Side Validations (e.g. Mandatory Requirements, email format…)
    -Responsive Application
    -Display of Validation errors

## Security Features: 

	-Password Hashing for storage in database using (Bcrypt-HashSalt) – (Server Side)
	-JWT Token Authentication
    -Spring Security
	-Password Encryption using AES-CBC – (Application Layer)
	-Authorization Guard
    -JWT Token Interception
    -Error Interception
