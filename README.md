# Simple-ATM

In order to run this software, your local machine must have Java (at least Java 8), additionally for compilation, Gradle must be installed properly (installation steps provided below).

### How to build, execute, and test the program:

#### Option 1 - Compiling and running the build with Gradle

**A. Installation steps:**

*(1) Installing Java 11:*

i. [Windows](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA)

ii. [Mac](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE)

iii. [Linux](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8)

*(2) Installing Gradle:*

Locate your OS in the link below and follow instructions carefully - once both Gradle and Java are installed correctly, the program will be ready for use.

[Gradle install link](https://gradle.org/install/)



**B. Running the Program:**

- Clone the master branch on to your local machine into a directory of your choosing
- In your terminal, locate the directory, and enter the directory `Simple-ATM`
- Run the command `gradle run`
- The program is now running, allowing you to interact with it appropriately

**C. Testing the Program:**

- In your terminal, locate the directory, and enter the directory `Simple-ATM`
- Run the command `gradle test`
- The program will run its test suite, indicating any failed tests

**D. Contributing to the Codebase:**

- Create a personal fork of the project on Github and clone the fork on your local machine
- Add the original repository as a remote called `upstream`
- Create a new branch from master to work on
- Implement/fix/comment your feature
- Run existing test cases to ensure your feature has been implemented properly
- Add or change the documentation as needed.
- Squash your commits into a single commit with git's interactive rebase.
- Push your branch to your fork on Github, the remote `origin`.
- Open a pull request in the `master` branch
- The pull request will then be inspected for approval and merging

#### Option 2 - Running the compiled .jar executable from Jenkins

##### A. Download the executable file

- Navigate to the project's Jenkins [build artifacts page](http://jenkins.kranz.com.au/job/assignment1/lastSuccessfulBuild/artifact/).
- Download the compressed file by clicking **(all files in zip)**.
- Extract the zip file
- Navigate to `archive/executable/`
- Double-click on the *run* file.

If this does not work, i.e. your operating system is not configured to run shell scripts:

- Open the archive/executable/ folder in your shell
- Type `java -jar app.jar` to execute the app.



### How to use the program

#### Normal user functions

For the purposes of testing the usage of the normal user functions, you can use the demo account `Gareth Bale`. The login details are as follows

```
Username: Gareth Bale
Card Number: 01342
PIN: 2364

This account is a user.
```



Upon execution, you will be greeted with

```bash
Good Evening! Welcome to XYZ Bank Inc! We have several ATMs, currently from 1 - 5. Which ATM are you using today?
```

To proceed, select an ATM. For the purpose of this tutorial, we will select ATM 1 by entering `1` as a command, which prompts:

```
Please insert your card
```

From here the user can type in their card number. For the purpose of this tutorial, you can use the test user details.

Enter the card number, `01342` to login to this user.

Once your card is processed, enter your PIN and press return. For this tutorial, the PIN is `2364`.

After this step, you have four options:

##### Making a withdrawal

To make a withdrawal, enter `W`.

If we want to withdraw $40, we enter `40.00`

The money has now been withdrawn and the ATM process ends.

##### Making a deposit

To make a deposit, enter `D`.

If we have 5 $20 notes and 4 $10 notes ($140 total), we first select the denomination $20 by entering `4`.

For the quantity, we enter `5`.

When prompted to deposit another denomination, we enter `Y`, as there are more notes to deposit.

For the denomination, we enter `3`

For the quantity, we enter `4`.

As we are finished depositing money, we enter any key (exepct for `Y`) to end the process.

##### Checking your balance

To check our balance, we enter `B`.

Now we are prompted with our account balance, and the main menu options again

##### Cancelling transaction

To cancel your transaction and end the ATM process, enter `C`.

You will be prompted with `Thank you for choosing XYZ Bank, have a good day!`, and the process will terminate.



#### Admin restocking

For the purposes of testing the usage of the restock protocol, you can use the demo account `Admin Malpass`. The login details are as follows

```
Username: Admin Malpass
Card Number: 01010
PIN: 0101

This account is an administrator.
```



Upon execution, you will be greeted with

```bash
Good Evening! Welcome to XYZ Bank Inc! We have several ATMs, currently from 1 - 5. Which ATM are you using today?
```

To proceed, select an ATM. For the purpose of this tutorial, we will select ATM 1 by entering `1` as a command, which prompts:

```
Please insert your card
```

From here the user can type in their card number. For the purpose of this tutorial, you can use the test user details.

Enter the card number, `01010` to login to this user.

Once your card is processed, enter your PIN and press return. For this tutorial, the PIN is `0101`.

As an admin user, you will be prompted with options for restocking the ATM. For this tutorial, we will restock the ATM with 40x $50 dollar notes and 20x $20 notes.

Choose option `11` to select 50 dollar denominations.

When prompted how many 50 dollar notes to be restocked, we will enter `40`.

After the next prompt, we would like to further restock the ATM, so we enter `Y`.

Choose option `10` to select 50 dollar denominations.

When prompted how many 20 dollar notes to be restocked, we will enter `20`.

Now, as we are finished restocking, we simply enter any key (except for `Y`) to finish.

The new balance for the ATM should have increased by `$2400`.
