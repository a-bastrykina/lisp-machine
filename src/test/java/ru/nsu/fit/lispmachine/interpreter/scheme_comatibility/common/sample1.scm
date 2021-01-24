(define (append lhs rhs)
  (if (null? lhs)
      rhs
      (cons (car lhs) (append (cdr lhs) rhs))))

(define true #t)
(define false #f)

(define (square x)
  (* x x))

(define (cube x)
  (* x x x))

(define (double x)
  (* x 2))

(define (halve x)
  (/ x 2))

(define (even? n)
  (= (% n 2) 0))

(define (div a b)
  (floor (/ a b)))

(define (divides? a b)
  (= (% b a) 0))

(define (identity x) x)

(define close-enough?
  (lambda (a b delta) (< (abs (- a b)) delta)))

(define (average x y)
  (/ (+ x y) 2.0))

(define (average-of-3 x y z)
  (/ (+ x y z) 3.0))

(define (inc x)
  (+ x 1))

(define (gcd a b)
  (if (= b 0)
      a
      (gcd b (% a b))))


(define nil '())



(define (accumulate op initial sequence)
  (if (null? sequence)
    initial
    (op (car sequence)
        (accumulate op initial (cdr sequence)))))

(define (filter predicate sequence)
  (cond ((null? sequence) nil)
        ((predicate (car sequence))
         (cons (car sequence) (filter predicate (cdr sequence))))
        (else (filter predicate (cdr sequence)))))

(define (enumerate-leaves tree)
  (cond ((null? tree) nil)
        ((not (pair? tree)) (list tree))
        (else (append (enumerate-leaves (car tree))
                      (enumerate-leaves (cdr tree))))))

(define (enumerate-interval low high)
  (if (> low high)
    nil
    (cons low (enumerate-interval (+ low 1) high))))

(define (accumulate-n op init seqs)
  (if (null? (car seqs))
      nil
      (cons (accumulate op init (map car seqs))
            (accumulate-n op init (map cdr seqs)))))

(define (fold-left op initial sequence)
  (define (iter result rest)
    (if (null? rest)
        result
        (iter (op result (car rest))
              (cdr rest))))
  (iter initial sequence))

(define fold-right accumulate)

(define (reverse sequence)
  (fold-right (lambda (x y) (append y (list x))) nil sequence))




