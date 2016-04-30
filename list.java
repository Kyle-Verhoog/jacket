package jacket;

public class list {

    interface compare {
        public boolean cond(int i, int j);
    }

    // Defines the empty list (null)
    public static list empty = null;
    public Integer first;
    public list rest;

    public static int first(list lst) {
        return lst.first;
    }

    public static list cons(int x, list lst) {
        list t = new list();
        t.first = x;
        t.rest = lst;
        return t;
    }

    public static list rest(list lst) {
        if (empty(lst))
            return empty;
        lst = lst.rest;
        return lst;
    }

    public static boolean empty(list lst) {
        return lst == empty;
    }

    public static int second(list lst) {
        return first(rest(lst));
    }

    public static void print(list lst) {
        if (empty(lst))
            return;
        System.out.print(first(lst));
        if (!empty(rest(lst)))
            System.out.print(" ");
        print(rest(lst));
    }

    public static void pprint(list lst) {
        System.out.print("(");
        print(lst);
        System.out.print(")\n");
    }

    public static int len(list lst) {
        if (empty(lst))
            return 0;
        else
            return 1 + len(rest(lst));
    }

    public static boolean not(boolean i) {
        return !i;
    }

    public static boolean equal(int i, int j) {
        return i == j;
    }

    public static boolean equal(list lst1, list lst2) {
        if (empty(lst1) && empty(lst2))
            return true;
        if (empty(lst1) && not(empty(lst2)))
            return false;
        if (empty(lst2) && not(empty(lst1)))
            return false;
        if (not(equal(first(lst1), first(lst2))))
            return false;
        else
            return equal(rest(lst1), rest(lst2));
    }

    public static int sum(list lst) {
        if (empty(lst)) return 0;
        else
            return first(lst) + sum(rest(lst));
   }

    public static list append(list lst1, list lst2) {
        if (empty(lst1) && empty(lst2))
            return empty;
        if (not(empty(lst1)) && (not(empty(lst2))))
            return cons(first(lst1), append(rest(lst1), lst2));
        else
            return cons(first(lst2), append(lst1, rest(lst2)));
    }

    public static boolean and(boolean b1, boolean b2) {
        return (b1 == b2 ? (b1 ? true : false) : false);
    }

    private static boolean or(boolean b1, boolean b2) {
        return (not(b1) ? b2 : true);
    }

    public static boolean and(boolean... conds) {
        return and(0, conds.length, conds);
    }

    private static boolean and(int i, int j, boolean... conds) {
        if (i == j)
            return true;
        else
            return and(conds[i], and(i+1, j, conds));
    }

    public static boolean or(boolean... conds) {
        return or(0, conds.length, conds);
    }

    private static boolean or(int i, int j, boolean... conds) {
        if (i == j)
            return false;
        else
            return or(conds[i], or(i+1, j, conds));
    }


    public static list merge(list lst1, list lst2) {
        if (and(empty(lst1), empty(lst2)))
            return empty;
        else if (and(empty(lst1), not(empty(lst2))))
            return cons(first(lst2), merge(lst1, rest(lst2)));
        else if (and(not(empty(lst1)), empty(lst2)))
            return lst1;
        else if (first(lst1) < first(lst2))
            return cons(first(lst1), merge(rest(lst1), lst2));
        else
            return cons(first(lst2), merge(lst1, rest(lst2)));
    }

    public static list merge(list lst1, list lst2, compare c) {
        if (and(empty(lst1), empty(lst2)))
            return empty;
        else if (and(empty(lst1), not(empty(lst2))))
            return cons(first(lst2), merge(lst1, rest(lst2)));
        else if (and(not(empty(lst1)), empty(lst2)))
            return lst1;
        else if (c.cond(first(lst1), first(lst2)))
            return cons(first(lst1), merge(rest(lst1), lst2, c));
        else
            return cons(first(lst2), merge(lst1, rest(lst2), c));
    }


    public static int sub1(int i) {
        return i - 1;
    }

    public static list tail(list lst, int i) {
        if (empty(lst))
            return empty;
        else if (i > 0)
            return tail(rest(lst), sub1(i));
        else
            return cons(first(lst), tail(rest(lst), i));
    }

    public static list head(list lst, int i) {
        if (empty(lst))
            return empty;
        else if (equal(i, 0))
            return empty;
        else
            return cons(first(lst), head(rest(lst), sub1(i)));
    }


    // Returns the element a position i in the list
    // O(n)
    public static list get(list lst, int i) {
        return subl(lst, i, i);
    }

    // Return the sublist of lst between indices i and j (inclusive)
    // O(n)
    public static list subl(list lst, int i, int j) {
        return head(ignore(lst, i), j);
    }

    // Ignore the first n items of the list
    public static list ignore(list lst, int n) {
        if (empty(lst))
            return empty;
        else if (equal(n, 0))
            return cons(first(lst), ignore(rest(lst), n));
        else
            return ignore(rest(lst), sub1(n));
    }

    // Classic mergesort, does not store length
    // O(n^2log(n)) 
    public static list mergesort(list lst) {
        return mergesort(lst, len(lst));
    }

    private static list mergesort(list lst, int len) {
        if (empty(rest(lst)))
            return lst;
        else
            return merge(mergesort(head(lst, len/2)),
                         mergesort(tail(lst, len/2)));
    }

    public static list mergesort(list lst, compare c) {
        if (empty(rest(lst)))
            return lst;
        else
            return merge(mergesort(head(lst, len(lst)/2), c),
                         mergesort(tail(lst, len(lst)/2), c), c);
    }

    public static list ipunion(list lst1, list lst2) {
        if (or(empty(lst1), empty(lst2)))
            return empty;
        else if (equal(first(lst1), first(lst2)))
            return cons(first(lst1), ipunion(rest(lst1), rest(lst2)));
        else
            return ipunion(rest(lst1), rest(lst2));
    }

    // Returns the unique intersection of the two lists
    public static list intersec(list lst1, list lst2) {
        return intersec(lst1, lst2, empty);
    }

    private static list intersec(list lst1, list lst2, list u) {
        if (or(empty(lst1), empty(lst2)))
            return empty;
        else if (and(in(first(lst1), lst2), not(in(first(lst1), u))))
            return cons(first(lst1), intersec(rest(lst1), lst2));
        else
            return intersec(rest(lst1), lst2);
    }

    public static boolean in(int i, list lst) {
        if (empty(lst))
            return false;
        else if (equal(i, first(lst)))
            return true;
        else
            return in(i, rest(lst));
    }
}
