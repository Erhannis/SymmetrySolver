So, here's the idea.  If you take a die, or some other polyhedron composed of identical faces (possibly allowing reflection if you have an asymmetric face; not sure), and you draw colored arrows around the edges of one face, what mappings of edge to mating edge are internally consistent?  Like, for instance, you have a d8 (octahedron) and you pick a face and draw a red arrow, green arrow, and blue arrow going clockwise around the edges.  (R, G, and B.  Mirrored shall be denoted R* etc.)  Under a mapping F where F(R)=R, the edge with R on it abuts an edge with an R arrow going the other way.  (If it went the same way, the far face would be a mirror of the near face.)  So, let's suppose the following mapping:
F(R)=R*
F(G)=B
F(B)=G
Then alongside the red arrow there would be a red arrow facing the same direction, alongside the green arrow there would be a blue arrow facing the opposite direction, and alongside the blue arrow there would be a green arrow facing the opposite direction.  If you complete the faces you've begun, by filling out the faces in RGBRGBRGB order (following the arrow directions), you should be able to fill up the d8 without violating any of the rules.  (On my notes I'm actually going counterclockwise, so forgive me if I turn out to be wrong, but I'm *pretty* sure this all should work the same if you mirror the whole die.)

Now, on the other hand, consider the following mapping:
F(R)=R
F(G)=G
F(B)=B
So alongside each arrow of a given color, there is another arrow (in the opposing face) running the opposite direction.  If you propagate this pattern out, you should eventually reach a point where you've drawn in an R alongside a G, or something, violating the rules, demonstrating that the mapping is invalid for a d8.

This program is intended to help find which mappings are valid for a given polyhedron.

Side note, since I wrote them all out - I believe that there are only 8 unique mappings possible for anything composed of a triangle (and not all are necessarily valid) :
F(RGB)=
RGB
RGB*
RG*B*
RBG
RB*G*
R*G*B*
R*BG
R*B*G*

I *think* such mappings are equivalent to like, symmetry groups or something, but I'm not totally sure.

I may extend the program to deal with transformations in 3d space, after I mess with the mappings.

This program is released under the Apache 2.0 License.

-Erhannis