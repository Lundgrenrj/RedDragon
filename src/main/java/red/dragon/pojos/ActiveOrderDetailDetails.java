package red.dragon.pojos;

public class ActiveOrderDetailDetails
{
	private String name;
	private String description;
	private double price;
	private String specialInstructions;
	private int sequence;
	
	public ActiveOrderDetailDetails()
	{
		name = new String();
		description = new String();
		specialInstructions = new String();
		price = 0;
		sequence = 0;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getSpecialInstructions()
	{
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions)
	{
		this.specialInstructions = specialInstructions;
	}

	public int getSequence()
	{
		return sequence;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

}
