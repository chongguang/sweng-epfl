# CS305 – Software Engineering

## Fall 2014

## Final Exam – Theory

This final exam has two parts, theory and practice.

The theory part is worth 20 points; there are a total of 15 questions in this first part of the exam, each worth 1.33 points.

Unless noted, each question has a single correct answer.

The practice part is a series of programming exercises, and is worth 80 points of the 100 points on the exam.

Please plan your time accordingly.

** Please fill in the theory part by marking an 'x' in the appropriate boxes below (``[x]``). Don't forget to commit this file and push it to Github! **

### Question 1

A  `Stack`  can be implemented using a `Vector`. The stack abstraction can either be implemented by using inheritance or by delegation. Which approach is preferable and why? Choose all that apply.

- [ ] Inheritance, because it eliminates the need to duplicate code, for example, `isEmpty`.
- [ ] Inheritance, because the `Stack` should behave as a `Vector` when it is cast to one.
- [x] Delegation, because it hides the representation of the data and prevents inappropriate operations such as inserting a value in the middle of the stack.
- [ ] Delegation, because `Stack` needs to define `Push` and `Pop` methods, which violates the Liskov Substitution Principle (LSP).


### Question 2

Below is the code used in a server's login implementation. Which passwords would give a user access to the site?

```sql
SELECT user FROM users WHERE pass = "$PASSWORD"
```

- [ ] ``s3cr3t``
- [ ] ``12345``
- [ ] ``$PASSWORD``
- [ ] ``" OR 1 = "1``
- [ ] The empty string
- [x] Anything that is longer than 4096 characters



### Question 3

Is there a way to recover a password from the following database dump of unsalted but encrypted passwords?

`user | hashed_password`

`root | a4d80eac9ab26a4a2da04125bc2c096a`

- [ ] Yes, the password is ``a4d80eac9ab26a4a2da04125bc2c096a``.
- [ ] Yes, the password is ``root``.
- [x] Yes, the password is ``s3cr3t``.
- [ ] No.


### Question 4

A Swiss company hosts a web application that they want to make accessible to countries in other parts of the world (e.g., U.S. or Asia). The application load is light, but unfortunately the speed of light is fixed, so that end-to-end response time between Switzerland and remote places can be as high as 1000ms, much higher than the response time inside Switzerland (<150ms), and hence more likely to be noticeable to the users.

What would be a good approach for the company to improve the user experience for their app? Check all that apply.

- [ ] Compress the application’s messages to reduce the number of bytes transmitted.
- [ ] Replicate the application database in locations closer to the end users and serve the data from the replicas.
- [x] Preload or cache as much as possible of the application static data on the client side (e.g. in the browser).
- [x] If the application data can be degraded (e.g., reduce video quality), do so for users with slow Internet connections.


### Question 5

A compression algorithm needs to find the minimum number of bits necessary to store a given positive Java `long` value. The code currently computes this by: `(int)(Math.log(value)/Math.log(2))`, since the base 2 logarithm of a number gives the number of bits need to represent the number.

What is the most effective way of improving the performance of this piece of code?

- [ ] This is the most efficient implementation, since it uses the Java standard Math library, which is itself implemented efficiently.
- [x] The number of bits for each possible value can be precomputed in a table, and the value itself serves as the index in the table to retrieve the result in constant time.
- [ ] The code can be improved by computing Math.log(2) only once and then used as a constant.
- [ ] The number of bits can be obtained by analyzing the bit structure of the number through bit shifting operations.
- [ ] A cache of the last N computed values could be stored in a map data structure.


### Question 6

Imagine an Android calculator app that uses buttons and textfields to display the calculator interface, has a mediation class that takes user inputs and relays them to necessary components, and a collection of classes that perform arithmetic operations.

Which of the following MVC pattern elements correspond to the interface, mediation classes, and arithmetic classes respectively?

- [ ] Model, view, controller
- [x] View, controller, model
- [ ] View, model, controller
- [ ] Controller, model, view


### Question 7

Consider the following code fragment:

```c
/* Read type and payload length first */
        hbtype = *p++;
        n2s(p, payload);
        pl = p;
. . .
         if (hbtype == TLS1_HB_REQUEST)
                {
. . .
                buffer = OPENSSL_malloc(1 + 2 + payload + padding);
                bp = buffer;
                
                *bp++ = TLS1_HB_RESPONSE;
                s2n(payload, bp);
                memcpy(bp, pl, payload);
                
                r = ssl3_write_bytes(s, TLS1_RT_HEARTBEAT, buffer, 3 + payload + padding);`
```
What is the problem with this code? Choose all that apply.

- [ ] No problem.
- [x] Buffer overrun error that can cause a segmentation fault (SEGFAULT).
- [ ] Information leakage error that can leak private keys.
- [x] Poor choice of variable names.
- [x] Lack of defensive programming.


### Question 8

What is the best strategy for optimizing the performance of your code?

- [ ] For each function or method, re-work its implementation until it runs as fast as possible.
- [ ] Write the smallest implementation possible, as it will cause fewer instructions to be executed.
- [x] Write a correct implementation, then measure it with realistic inputs and optimize its slowest parts.
- [ ] Write a fast, but incomplete implementation, then add the missing features.


### Question 9

Suppose you are working on a large program that stores its data in a complex data structure. In refactoring the program, you realize that the iterator design pattern would help improve the code. Unfortunately, you cannot change the implementation of the data structure and must use its existing interface.

Which of the following methods are not necessary to implement an iterator? Choose all that apply. There may be more than one correct answer to this question -- be sure that you leave a consistent set of methods for your iterator.

- [ ] `GetItem(i)` -- Item i or null if no such element exists
- [x] `HasItem(i)` -- True if item i exists
- [x] `IsModifiedSinceLastCall()` -- True if structure is modified since this method last called
- [ ] `Size()` -- Number of elements in structure


### Question 10

Consider the following class:

```java
class CS implements SetInterface {
    private StringBuffer s;
    CS() {
        s = new StringBuffer();                         // #1
    }
    public void add(char ch) {
        s.append(ch);                                   // #2
    }
    public void remove(char ch) {
        int index = s.indexOf(String.valueOf(ch));
        if (index >= 0) {
            s.deleteCharAt(index);                      // #3
        }
    }
    public boolean isMember(char ch) {
        return s.indexOf(String.valueOf(ch)) != -1;    // #4
    }
}
```

It implements the following abstraction:

```java
interface SetInterface {
    void add(char c);                                      Set ← Set ∪ {c}          
    void remove(char c);                                   Set ← Set \ {c}
    boolean isMember(char c);                              c ∈ Set ⇒ return true ∧ c ∉ Set ⇒ return false
}
```

Which line of the code contains the root cause error that violates the interface abstraction?

- [ ] #1
- [x] #2
- [ ] #3
- [ ] #4


### Question 11      

A static code analysis tool:

- [ ] Must avoid false negatives since people will not use it if it does not find all bugs.
- [x] Should attempt to avoid false positives since people will not use it if it finds too many spurious issues.
- [ ] Should focus on discovering complex issues that code reviews and pair programming cannot find.


### Question 12    

Mark all of the following statements that are correct assertions about testing:

- [x] White-box testing can theoretically explore all paths in a program.
- [ ] Randomized black-box testing can theoretically explore all paths in a program.
- [ ] White-box testing finds more bugs than randomized black-box testing, because randomized testing has a low probability of exploring a particular path.
- [x] White-box and black-box testing are automated testing methods that can augment a human-written test suite.
- [ ] Randomized testing is bad because most of the tests that it produces do not fail.


### Question 13

Mark all of the following statements that are correct assertions about optimization:

- [ ] A sampling profiler can tell how often a particular function has been executed.
- [x] A sampling profiler can tell what fraction of execution time was spent in a particular function.
- [x] An instrumentation-based profiler can tell how often a function was executed.
- [ ] Because they are more precise, instrumentation-based profilers are more useful than sampling profilers.
- [] Reading a random byte from disk is around 10^5 times slower than reading it from RAM.
- [ ] Disk throughput is around 10^5 times lower than RAM throughput.


### Question 14

A class hierarchy that follows the Liskov Substitution principle:

- [ ] Means an object can be replaced by an instance of its superclass anywhere in the program.
- [ ] Is more easily testable because there are fewer problems when objects are replaced by mocks.
- [ ] Can be developed more easily if class fields are immutable.
- [ ] Implies that subclass methods have postconditions that are at least as strict as those of the superclass method.


### Question 15

A secure communication protocol allows two parties to choose one of several available encryption schemes. What would be a good design pattern to use when implementing this type of protocol?

- [ ] Create singleton objects for each encryption scheme supported.
- [ ] Use the strategy design pattern to separate the protocol implementation from the encryption scheme.
- [ ] Modify the protocol to let one of the parties send a JAR library with the encryption code to the other party.
- [ ] Use the Model-View-Controller pattern, by considering the protocol to be the model and the encryption scheme the controller.
- [ ] Use the Model-View-Controller pattern, by considering the protocol to be the controller and the encryption scheme the model.


### Special Question

Code indentation should:

- [ ] Use 1 tab character (this saves storage space and is the default in Eclipse).
- [ ] Use two spaces (this helps people with narrow screens).
- [ ] Use four spaces (a study showed that this improves clarity the most, and the Java coding conventions mandate it).
- [ ] Use three spaces (a good compromise between two and four).
- [ ] It doesn’t matter (the compiler ignores indentation anyway).
- [ ] Whatever is consistent with the surrounding code.
- [ ] 42 (it’s always the answer to this kind of question).
- [ ] null (I cannot answer this question without endangering world peace).
- [ ] NaN (this is not funny... exams are supposed to be serious, stop asking questions like this one).

