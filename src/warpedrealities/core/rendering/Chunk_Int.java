package warpedrealities.core.rendering;

import warpedrealities.core.core.Tile_Int;
import warpedrealities.core.shared.Vec2f;

public interface Chunk_Int {

	Tile_Int[][] getTiles(int i);

	Vec2f getPos();
}
