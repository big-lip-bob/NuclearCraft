package nc.multiblock.heatExchanger.tile;

import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.heatExchanger.HeatExchanger;
import nc.multiblock.heatExchanger.block.BlockHeatExchangerController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileHeatExchangerController extends TileHeatExchangerPartBase {
	
	public TileHeatExchangerController() {
		super(CuboidalPartPositionType.WALL);
	}
	
	@Override
	public void onMachineAssembled(HeatExchanger controller) {
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		if (getWorld().isRemote) return;
	}
	
	@Override
	public void onMachineBroken() {
		super.onMachineBroken();
		if (getWorld().isRemote) return;
		//getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()), 2);
	}
	
	@Override
	public void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos) {
		super.onBlockNeighborChanged(state, world, pos, fromPos);
		if (getMultiblock() != null) getMultiblock().setIsHeatExchangerOn();
	}
	
	public void updateBlock(boolean active) {
		if (getBlockType() instanceof BlockHeatExchangerController) {
			((BlockHeatExchangerController)getBlockType()).setActiveState(getBlockState(pos), world, pos, active);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}
}
