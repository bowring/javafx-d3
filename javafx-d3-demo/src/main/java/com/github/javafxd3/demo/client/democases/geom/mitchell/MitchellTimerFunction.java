package com.github.javafxd3.demo.client.democases.geom.mitchell;

import com.github.javafxd3.api.core.Selection;
import com.github.javafxd3.api.functions.TimerFunction;

public class MitchellTimerFunction implements TimerFunction {

	//#region ATTRIBUTES

	private double numberOfCandidatesPerCircle = 1; // initial number of candidates to consider per circle
	private double numberOfCirclesPerFrame = 10; // initial number of circles to add per frame
	private double remainingNumberOfCircles = 2500; // remaining number of circles to add

	private CircleGenerator circleGenerator;
	private Selection svg;
	private MitchellBestCandidate parent;

	//#end region

	//#region CONSTRUCTORS

	public MitchellTimerFunction(CircleGenerator circleGenerator, Selection svg, MitchellBestCandidate parent) {
		this.circleGenerator = circleGenerator;
		this.svg = svg;
		this.parent = parent;

	}
	//#end region

	//#region METHODS

	//#end region

	@Override
	public boolean execute() {
		for (int i = 0; (i < numberOfCirclesPerFrame) && (--remainingNumberOfCircles >= 0); ++i) {
			Circle circle = circleGenerator.generate(numberOfCandidatesPerCircle);

			svg.append("circle").attr("cx", circle.x).attr("cy", circle.y).attr("r", 0)
					.style("fill-opacity", (Math.random() + .5) / 2).transition().attr("r", circle.r);

			// As we add more circles, generate more candidates per
			// circle.
			// Since this takes more effort, gradually reduce circles
			// per frame.
			if (numberOfCandidatesPerCircle < 500) {
				numberOfCandidatesPerCircle *= 1.01;
				numberOfCirclesPerFrame *= .998;
			}
		}
		return remainingNumberOfCircles == 0 || parent.getDone();
	}

}
