package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    List<Vertex> path;
    DoubleMapPQ<Vertex> pq;
    double timeElapsed, solWeight;
    boolean timedOut, finished;
    int opCount;
    Vertex endF, startF;

    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, Double> heuristic;
    HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        double startTime = System.nanoTime();

        path = new ArrayList<>();
        pq = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        heuristic = new HashMap<>();
        edgeTo = new HashMap<>();
        solWeight = 0;
        startF = start;
        endF = end;

        pq.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);

        if (start.equals(end)) {
            finished = true;
            path.add(pq.removeSmallest());
            opCount++;
        }

        Vertex spotlight = start;
        while (pq.size() > 0 && !spotlight.equals(end) && timeElapsed < timeout) {
            spotlight = pq.removeSmallest();
            path.add(spotlight);

            for (WeightedEdge<Vertex> edge : input.neighbors(spotlight)) {
                //Test: possible difference between edge.to and edge.from, switch if it doesn't work
                Vertex p = edge.from();
                Vertex q = edge.to();

                if (!distTo.containsKey(q)) {
                    distTo.put(q, Double.POSITIVE_INFINITY);
                    edgeTo.put(q, p);
                }

                heuristic.put(q, input.estimatedDistanceToGoal(q, end));
                relax(edge);
            }

            opCount++;

            double endTime = System.nanoTime();
            timeElapsed = (endTime - startTime) / 1000000000;
            if (timeElapsed >= timeout) {
                timedOut = true;
                break;
            }
        }

        if (spotlight.equals(end)) {
            finished = true;
        }

        if (outcome().equals(SolverOutcome.SOLVED)) {
            solWeight = distTo.get(end);
        }

    }

    private void relax(WeightedEdge<Vertex> edge) {
        Vertex p = edge.from();
        Vertex q = edge.to();
        double w = edge.weight();
        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.replace(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + heuristic.get(q));
            } else {
                pq.add(q, distTo.get(q) + heuristic.get(q));
            }
        }
    }

    public SolverOutcome outcome() {
        if (pq.size() == 0 && !finished) {
            return SolverOutcome.UNSOLVABLE;
        } else if (timedOut) {
            return SolverOutcome.TIMEOUT;
        }
        return SolverOutcome.SOLVED;
    }


    public List<Vertex> solution() {
        if (!outcome().equals(SolverOutcome.SOLVED)) {
            return new Stack<>();
        }

        Stack<Vertex> stack = new Stack<>();
        ArrayList<Vertex> result = new ArrayList<>();
        Vertex slide = endF;
        while (!slide.equals(startF)) {
            stack.push(slide);
            slide = edgeTo.get(slide);
        }
        stack.push(startF);
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    public double solutionWeight() {
        return solWeight;
    }

    public int numStatesExplored() {
        return opCount;
    }

    public double explorationTime() {
        return timeElapsed;
    }
}
