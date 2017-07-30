package gamedata.composition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gamedata.compositiongen.*;
import types.BasicActorType;

/**
 * This data/property set lets your actor damage every specific type in its radius at a rate you set.
 * Attach it to a projectile to wipe out your enemies!
 * 
 * @author maddiebriere
 */

public class DamageInRadiusData extends ActData {
	private Double radius;
	private Double damage; 
	private Integer damageRate;
	private List<BasicActorType> targets;
	
	public DamageInRadiusData(){
		this(0.0, 0.0, 0, new BasicActorType[0]);
	}
	
	public DamageInRadiusData(Double myHitRadius, Double myDamage, Integer damageRate, BasicActorType... myTypes) {
		this(myHitRadius, myDamage, damageRate, Arrays.asList(myTypes));
	}
	
	public DamageInRadiusData(Double myHitRadius, Double myDamage, Integer damageRate, List<BasicActorType> myTypes) {
		this.radius = myHitRadius;
		this.damage = myDamage;
		this.damageRate = damageRate;
		this.targets = new ArrayList<>();
		for (BasicActorType e : myTypes) {
			this.targets.add(e);
		}
	}
	
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	public Double getDamage() {
		return damage;
	}
	public void setDamage(Double damage) {
		this.damage = damage;
	}
	public List<BasicActorType> getTargets() {
		return targets;
	}
	public void setTargets(List<BasicActorType> targets) {
		this.targets = targets;
	}

	public Integer getDamageRate() {
		return damageRate;
	}

	public void setDamageRate(Integer damageRate) {
		this.damageRate = damageRate;
	}
	
}
