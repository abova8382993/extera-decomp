package com.yandex.mapkit.search;

import com.yandex.mapkit.geometry.Direction;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Span;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class Panorama implements Serializable {
    private Direction direction;

    /* JADX INFO: renamed from: id */
    private String f692id;
    private Point point;
    private Span span;

    public Panorama(String str, Direction direction, Span span, Point point) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"id\" cannot be null");
            throw null;
        }
        if (direction == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"direction\" cannot be null");
            throw null;
        }
        if (span == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"span\" cannot be null");
            throw null;
        }
        if (point == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"point\" cannot be null");
            throw null;
        }
        this.f692id = str;
        this.direction = direction;
        this.span = span;
        this.point = point;
    }

    public Panorama() {
    }

    public String getId() {
        return this.f692id;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Span getSpan() {
        return this.span;
    }

    public Point getPoint() {
        return this.point;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f692id = archive.add(this.f692id, false);
        this.direction = (Direction) archive.add(this.direction, false, (Class<Direction>) Direction.class);
        this.span = (Span) archive.add(this.span, false, (Class<Span>) Span.class);
        this.point = (Point) archive.add(this.point, false, (Class<Point>) Point.class);
    }
}
