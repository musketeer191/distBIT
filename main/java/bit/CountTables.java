package bit;

import org.petuum.jbosen.table.DoubleTable;

import defs.Dimensions;
import defs.Pair;

public class CountTables {
	
	public Dimensions dims;
	
	public DoubleTable topicUser; 
	public DoubleTable decisionUser;
	public DoubleTable itemTopic; 
	public DoubleTable brandTopic; 
	public DoubleTable itemBrand;

	public CountTables(Dimensions dims, DoubleTable topicUser,
			DoubleTable decisionUser, DoubleTable itemTopic,
			DoubleTable brandTopic, DoubleTable itemBrand) {
		
		this.dims = dims;
		this.topicUser = topicUser;
		this.decisionUser = decisionUser;
		this.itemTopic = itemTopic;
		this.brandTopic = brandTopic;
		this.itemBrand = itemBrand;
	}



	public void decTopicCount(int cTopic, int userIndex, int itemIndex, int cBrandIndex, int cDecision) {
		
		topicUser.inc(cTopic, userIndex, -1);
		topicUser.inc(dims.numTopic, userIndex, -1);
		
		if (cDecision == 0) {// topic-item
			itemTopic.inc(itemIndex, cTopic,  -1);
			itemTopic.inc(dims.numItem, cTopic, -1);
		} else {// topic-brand-item
			brandTopic.inc(cBrandIndex, cTopic, -1);
			brandTopic.inc(dims.numBrand, cTopic, -1);
		}
		
	}
	
	public void incTopicCount(int nTopic, int userIndex, int itemIndex, int cBrandIndex, int cDecision) {
		
		topicUser.inc(nTopic, userIndex,  1);
		topicUser.inc(dims.numTopic, userIndex, 1);
		
		if (cDecision == 0) {// topic-item
			itemTopic.inc(itemIndex, nTopic,  1);
			itemTopic.inc(dims.numItem, nTopic, 1);
			
		} else {// topic-brand-item
			brandTopic.inc(cBrandIndex, nTopic,  1);
			brandTopic.inc(dims.numBrand, nTopic, 1);
		}
	}
	
	public void decPairCount(Pair cPair, int userIndex, int itemIndex, int cTopic) {
		
		if (cPair.getDecision() == 0) {// currently no brand is used
//			System.out.println("currently no brand is used");
			decisionUser.inc(0, userIndex,  -1);
			decisionUser.inc(Dimensions.numDecision, userIndex, -1);	// decrease marginal decision counts
			
			itemTopic.inc(itemIndex, cTopic,  -1);
			itemTopic.inc(dims.numItem, cTopic, -1);
		} 
		else {//currently brand is used
//			System.out.println("currently brand is used");
			decisionUser.inc(1, userIndex,  -1);
			decisionUser.inc(Dimensions.numDecision, userIndex, -1);	// decrease marginal decision counts
			
			int cBrandIndex = cPair.getBrandIndex();
			
			brandTopic.inc(cBrandIndex, cTopic,  -1);
			brandTopic.inc(dims.numBrand, cTopic, -1);
			
			itemBrand.inc(itemIndex, cBrandIndex,  -1);
			itemBrand.inc(dims.numItem, cBrandIndex, -1);
			

		}
	}
	
	public void incPairCount(Pair nPair, int userIndex, int itemIndex, int cTopic) {
		
		if (nPair.getDecision() == 0) {
//			System.out.println("new decison: brand was not used");
			decisionUser.inc(0, userIndex,  1);
			decisionUser.inc(Dimensions.numDecision, userIndex, 1);	// increment marginal decision counts
			
			itemTopic.inc(itemIndex, cTopic,  1);
			itemTopic.inc(dims.numItem, cTopic, 1);
		} else {
//			System.out.println("new decison: brand was used");
			decisionUser.inc(1, userIndex,  1);
			decisionUser.inc(Dimensions.numDecision, userIndex, 1);	// increment marginal decision counts
			
			int nBrandIndex = nPair.getBrandIndex();
//			System.out.println("that brand was " + nBrandIndex);
			
			brandTopic.inc(nBrandIndex, cTopic,  1);
			brandTopic.inc(dims.numBrand, cTopic, 1);
			
			itemBrand.inc(itemIndex, nBrandIndex,  1);
			itemBrand.inc(dims.numItem, nBrandIndex, 1);
			
			
		}
	}

	
}
