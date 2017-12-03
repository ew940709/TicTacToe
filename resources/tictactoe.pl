:- dynamic
    xself/1,
    xenemy/1.


self((X,Y)) :- xself((X,Y)), !.
enemy((X,Y)) :- xenemy((X,Y)), !.

neighbour((X, Y), Candidate) :-
    Right is X + 1,
    Left  is X - 1,
    Up    is Y - 1,
    Down  is Y + 1,
    (
        Candidate = (Right, Y);
        Candidate = (Left, Y);
        Candidate = (X, Up);
        Candidate = (X, Down);
        Candidate = (Right, Up);
        Candidate = (Right, Down);
        Candidate = (Left, Down);
        Candidate = (Left, Up)
    ).

really_empty(Position) :-
    \+self(Position), \+enemy(Position).

has_non_empty_neighbour(Position) :-
    (self(Neighbour); enemy(Neighbour)),
    neighbour(Neighbour, Position).

empty(Position) :-
    has_non_empty_neighbour(Position),
    really_empty(Position).

moved((PosX, PosY), (DirX, DirY), (NewX, NewY)) :-
    NewX is PosX + DirX,
    NewY is PosY + DirY.

is_line(Player, Start, _, 1) :- call(Player, Start).
is_line(Player, Start, Direction, Length) :-
    call(Player, Start),
    moved(Start, Direction, Neighbour),
    NewLength is Length - 1,
    is_line(Player, Neighbour, Direction, NewLength).

is_line(Player, Start, Length) :- is_line(Player, Start, (0, 1), Length). % s
is_line(Player, Start, Length) :- is_line(Player, Start, (1, 0), Length). % e
is_line(Player, Start, Length) :- is_line(Player, Start, (1, 1), Length). % se

win(Player) :- is_line(Player, _, 5).

can_extend_line_of_length(Player, Position, Direction, Length) :-
    empty(Position),
    moved(Position, Direction, Moved),
    is_line(Player, Moved, Direction, Length).


can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, ( 0, -1), Length). % n
can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, ( 1, -1), Length). % ne
can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, ( 1,  0), Length). % e
can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, ( 1,  1), Length). % se
can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, ( 0,  1), Length). % s
can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, (-1,  1), Length). % sw
can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, (-1,  0), Length). % w
can_extend_line_of_length(Player, Position, Length) :- can_extend_line_of_length(Player, Position, (-1, -1), Length). % nw


win_in_one_move(Player, Position) :-
    can_extend_line_of_length(Player, Position, 4).

next_move(Position) :- win_in_one_move(self, Position), !.
next_move(Position) :- win_in_one_move(enemy, Position), !.
next_move(Position) :- can_extend_line_of_length(self, Position, 3), !.
next_move(Position) :- can_extend_line_of_length(self, Position, 2), !.
next_move(Position) :- can_extend_line_of_length(self, Position, 1), !.
next_move(Position) :- can_extend_line_of_length(enemy, Position, 1), !.
next_move((0, 0)).

remember_enemy(Position) :- assertz(xenemy(Position)).
remember_self(Position) :- assertz(xself(Position)).

start_new_game :- retractall(xself(_)), retractall(xenemy(_)).