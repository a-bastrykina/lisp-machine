
(define (trapezoid-square x1 x2 h) (* (/ (+ x1 x2) 2) h))

(define (trapezoids-sum f h xcur)
  (define next (- xcur h))
     (if (< xcur h)
       0
       (+ (trapezoid-square (f xcur) (f next) h) (trapezoids-sum f h next))
       )
)


(define (generic-integral f integrate-func)
  (define h 0.1)
    (lambda (x) (
      (begin
      (define nearest (* h (java-call "ru.nsu.fit.lispmachine.machine.SchemeMachineUtils" "floor" (/ x h))))
      (display nearest)
      (newline)
      (define restx (- x nearest))
      (display restx)
            (newline)
        (lambda ()(+ (integrate-func f h nearest) (trapezoid-square (f nearest) (f x) restx)))
        ))
    )
    )

(define (get-integral f) (generic-integral f trapezoids-sum))