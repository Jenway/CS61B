/** An Integer tester created by Flik Enterprises. */
public class Flik {
//    这个程序的目的是检测两个整数是否相等。它使用了==运算符来比较两个Integer对象是否相等。
//    然而，这里有一个陷阱，因为Integer是一个类而不是基本数据类型，所以==比较的是对象引用而不是值。
//    在Java中，对于Integer对象，只有当两个对象引用指向同一个内存地址时，才会返回true。
//    但是对于较小的整数，Java会使用缓存，因此对于相同值的较小整数，它们会共享相同的对象引用，导致==返回true。
//    但对于较大的整数，它们不会共享相同的对象引用，==比较将返回false。
//    这就是为什么这个程序可能会出错。
//    它可以在一些情况下给出正确的结果（比如比较较小的整数），但在其他情况下会得到错误的结果（比如比较较大的整数）。

    public static boolean isSameNumber(Integer a, Integer b) {
//        return a == b;
        return a.equals(b);
    }
}
