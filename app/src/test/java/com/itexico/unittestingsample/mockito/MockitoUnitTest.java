package com.itexico.unittestingsample.mockito;

import android.content.SharedPreferences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;



/**
 * Created by iTexico Developer on 8/5/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoUnitTest {

    @Mock
    private SharedPreferences mSharedPreferences;

    @Test
    public void behaviour() {
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void stubbing() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
//        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);
    }


    @Test
    public void argumentMatchers() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
//        when(mockedList.contains(argThat(isValid()))).thenReturn(Boolean.valueOf("element"));
        when(mockedList.contains(anyInt())).thenReturn(true);

        //following prints "element"
        System.out.println(mockedList.get(999));

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        //argument matchers can also be written as Java 8 Lambdas
//        verify(mockedList).add(someString -> someString.length() > 5);
        System.out.println(mockedList.contains(1000));
        verify(mockedList).contains(1000);

    }

    @Test
    public void exactNumberOfInvocations() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);
        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("five times");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    public void methodWithExceptions() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        doThrow(new RuntimeException()).when(mockedList).clear();
        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void verificationInOrder() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        InOrder mixedInOrder = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        mixedInOrder.verify(firstMock).add("was called first");
        mixedInOrder.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }


    @Test
    public void verifyInteractions() {
        //using mocks - only mockOne is interacted
        // A. Single mock whose methods must be invoked in a particular order
        List mockOne = mock(List.class);

        mockOne.add("one");

        //ordinary verification
        verify(mockOne).add("one");

        //verify that method was never called on a mock
        verify(mockOne, never()).add("two");

        //verify that other mocks were not interacted
        List mockTwo = mock(List.class);
        List mockThree = mock(List.class);
        verifyZeroInteractions(mockTwo, mockThree);
    }

    @Test
    public void redundantInvocations() {
        List mockedList = mock(List.class);

        //using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");
//        verify(mockedList).add("two");

        //following verification will fail
        verifyNoMoreInteractions(mockedList);
    }


    @Test
    public void shortHandMockCreation() {
        MockitoAnnotations.initMocks(mSharedPreferences);
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        System.out.print("\n shortHandMockCreation:" + sharedPrefEditor);
    }

    @Test
    public void consecutiveStubbing(){
        List mockedList = mock(LinkedList.class);
        when(mockedList.get(anyInt()))
                .thenThrow(new RuntimeException("Consecutive Stubbing Failed!!!"))
                .thenReturn("List element");
    }

    @Test
    public void callbacks(){
        List mock = mock(LinkedList.class);

        when(mock.get(anyInt())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return "called with arguments: " + args+","+mock.toString();
            }
        });

        //the following prints "called with arguments: foo"
        System.out.println(mock.get(anyInt()));
    }

    @Test
    public void familyOfMethods(){
        List mockedList = mock(LinkedList.class);
        doThrow(new RuntimeException()).when(mockedList).clear();
        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void spyRealObjects(){
        //Mockito *does not* delegate calls to the passed real instance, instead it actually creates a copy of it.
        List list = new LinkedList();
        List spy = spy(list);

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");

        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        when(spy.get(100)).thenReturn("foo");

        doReturn("foo").when(spy).get(100);

        //You have to use doReturn() for stubbing
        System.out.print(spy.get(100));
    }

}
