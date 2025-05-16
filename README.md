# Telstra Starter Repo :bird:

This repo has everything you need to get started on the Telstra program!


  First, read through this guide on integrating the JPA into a Spring microservice.
    For every request that makes its way to your microservice, you'll need to add a record to the database. This record should contain the ICCID of the SIM card, the associated customer's email address, and a Boolean value indicating whether the SIM card was activated successfully. The final flow of your programme should look like this:
        Your REST controller receives a new post request with a customer's email and an ICCID.
        Your REST consumer sends a post request to the SimCardActuator at http://localhost:8444/actuate with the ICCID of the SIM card received in the previous step.
        Your database connection saves a new record of the aforementioned transaction by creating a new row in the database with the following columns: 
            id: long
            (In keeping with best practices for relational databases, the ID column should include the @Id and @GeneratedValue annotations. These will allow the database to manage the column automatically, incrementing the value of the ID every time a new row is introduced. Since the database is going to manage this column, you may exclude it from your entity's constructors – do not set it manually.)
            iccid: string
            customerEmail: string
            active: boolean
    Since we're only interested in basic CRUD operations, you should use the JPA instead of raw SQL to simplify your programme. All the dependencies you need have already been added to the pom.xml file, so you can get started writing actual code right away!
    As a final step, add a query endpoint to your REST controller so you can test your programme in the next task. This endpoint will handle Get requests and accept a single request parameter called simCardId (of type: long). Upon receiving a request, this endpoint will query the database for the matching ID and return the values it finds as a JSON object in the response. The response object should have the following structure:

{

“iccid”: string,

“customerEmail”: string,

“active”: boolean

}
